package cn.edu.tongji.diaryWriting.service.impl;

import cn.edu.tongji.diaryWriting.service.BaiduSentimentAnalysis;
import jakarta.annotation.Resource;
import okhttp3.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

//调用百度文本情感分析的接口实现对日记文本情绪的分析
@Service
public class BaiduSentimentAnalysisImpl implements BaiduSentimentAnalysis {
    @Resource
    private float confidence;  //对文本情感分析的置信度
    @Resource
    private float negtiveProb;  //表示属于消极类别的概率，取值范围[0,1]
    @Resource
    private float positiveProb;  //表示属于积极类别的概率 ，取值范围[0,1]
    @Resource
    private float sentiment;  //表示情感极性分类结果，0:负向，1:中性，2:正向
    @Resource
    private String content;  //表示准备进行分析的文本

    public static void main(String[] args){

        String API_KEY = "0SCnD9Cs92xQC2GH96k3yXBg";
        String SECRET_KEY = "B7AzqjzAK6AvxtdQTZfehDLp3yGwuHdX";

        String accessToken = getAccessToken(API_KEY, SECRET_KEY);

        if (accessToken != null) {
            String url = "https://aip.baidubce.com/rpc/2.0/nlp/v1/sentiment_classify?access_token=" + accessToken;

            OkHttpClient client = new OkHttpClient();

            JSONObject requestBody = new JSONObject();
            requestBody.put("text", "但是上软工的课上点名了，我没有来呜呜呜");

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
                    float negtiveProb=item.getFloat("negative_prob");
                    float positiveProb=item.getFloat("positive_prob");
                    int sentiment=item.getInt("sentiment");
                    System.out.println("Confidence: " + confidence);
                    System.out.println("Negative_Prob: " + negtiveProb);
                    System.out.println("Positive_Prob: " + positiveProb);
                    System.out.println("sentiment: " + sentiment);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
