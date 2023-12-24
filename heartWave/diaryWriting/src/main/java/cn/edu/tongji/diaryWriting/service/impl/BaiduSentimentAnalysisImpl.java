package cn.edu.tongji.diaryWriting.service.impl;

import cn.edu.tongji.diaryWriting.model.DiaryEmotion;
import cn.edu.tongji.diaryWriting.service.BaiduSentimentAnalysis;
//import jakarta.annotation.Resource;
import okhttp3.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

//调用百度文本情感分析的接口实现对日记文本情绪的分析
@Service
public class BaiduSentimentAnalysisImpl implements BaiduSentimentAnalysis {
    private final DiaryEmotion sentimentResult = new DiaryEmotion();
    public DiaryEmotion sentimentAnalysis(String content){
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
                    sentimentResult.setSentiment(sentiment);
                    sentimentResult.setNegative(negativeProb);
                    sentimentResult.setConfidence(confidence);
                    sentimentResult.setPositive(positiveProb);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sentimentResult;
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
}
