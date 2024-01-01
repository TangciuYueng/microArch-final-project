package cn.edu.tongji.EmotionProcessor.service.impl;
import cn.edu.tongji.EmotionProcessor.client.DiaryEmotionClient;
import cn.edu.tongji.EmotionProcessor.client.DiaryWritingClient;
import cn.edu.tongji.EmotionProcessor.dto.DiaryDTO;
import cn.edu.tongji.EmotionProcessor.model.SentimentResult;
import cn.edu.tongji.Emotions.dto.DiaryEmotionDTO;
import jakarta.annotation.Resource;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Request;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import cn.edu.tongji.EmotionProcessor.service.DiaryEmotionAnalysisService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DiaryEmotionAnalysisServiceImpl implements DiaryEmotionAnalysisService {

    SentimentResult sentimentResult = new SentimentResult();
    DiaryWritingClient diaryWritingClient;
    DiaryEmotionClient diaryEmotionClient;

    public ResponseEntity<?> sentimentAnalysis(String content){
        String API_KEY = "0SCnD9Cs92xQC2GH96k3yXBg";
        String SECRET_KEY = "B7AzqjzAK6AvxtdQTZfehDLp3yGwuHdX";

        String accessToken = getAccessToken(API_KEY, SECRET_KEY);

        if (accessToken != null) {
            String url = "https://aip.baidubce.com/rpc/2.0/nlp/v1/sentiment_classify?access_token=" + accessToken;

            OkHttpClient client = new OkHttpClient();

            JSONObject requestBody = new JSONObject();
            requestBody.put("text", content);

            RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestBody.toString());
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String jsonResponse = response.body().string();

                System.out.println(jsonResponse);
                // 解析JSON响应
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONArray itemsArray = jsonObject.getJSONArray("items");
                //获取重要信息并且输出
                if (itemsArray.length() > 0) {
                    JSONObject item = itemsArray.getJSONObject(0);
                    float confidence = item.getFloat("confidence");
                    float negativeProb=item.getFloat("negative_prob");
                    float positiveProb=item.getFloat("positive_prob");
                    int sentiment=item.getInt("sentiment");

                    System.out.println("Confidence: " + confidence);
                    System.out.println("Negative_Prob: " + negativeProb);
                    System.out.println("Positive_Prob: " + positiveProb);
                    System.out.println("sentiment: " + sentiment);

                    sentimentResult.sentiment = sentiment;
                    sentimentResult.negative = negativeProb;
                    sentimentResult.confidence = confidence;
                    sentimentResult.positive = positiveProb;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok(sentimentResult);
    }

    private static String getAccessToken(String apiKey, String secretKey) {
        String url = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=" + apiKey + "&client_secret=" + secretKey;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            JSONObject jsonResponse = new JSONObject(response.body().string());
            return jsonResponse.getString("access_token");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Scheduled(fixedRate = 3600000)
    public void getDiaryEmotion() {
        ResponseEntity<?> diaryListResponse = diaryWritingClient.getRecentDiaries();
        if (diaryListResponse.getBody() instanceof List) {
            List<DiaryDTO> diaryList = (List<DiaryDTO>) diaryListResponse.getBody();
            for (DiaryDTO diary : diaryList) {
                ResponseEntity<?> sentimentAnalysisResult = sentimentAnalysis(diary.getContent());
                if (sentimentAnalysisResult.getBody() instanceof SentimentResult) {
                    SentimentResult sentimentResult = (SentimentResult) sentimentAnalysisResult.getBody();

                    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                    LocalDateTime createTime = LocalDateTime.parse(diary.getCreateDate(), formatter);

                    DiaryEmotionDTO diaryEmotionDTO = new DiaryEmotionDTO(
                            diary.getUserId(),
                            diary.getId(),
                            createTime,  // 设置当前时间为createTime
                            sentimentResult.sentiment,
                            sentimentResult.confidence,
                            sentimentResult.positive,
                            sentimentResult.negative
                    );

                    // createDiaryEmotion方法接受一个DiaryEmotionDTO对象
                    diaryEmotionClient.createDiaryEmotion(diaryEmotionDTO);
                }
            }
        }
    }


    // 你可能需要定义一个Diary类来表示日记数据

}
