package cn.edu.tongji.diaryWriting.service;

import cn.edu.tongji.diaryWriting.dto.SentimentData;

public interface BaiduSentimentAnalysis {
    SentimentData sentimentAnalysis(String content);
}
