package cn.edu.tongji.diaryWriting.service;

import cn.edu.tongji.Emotions.model.DiaryEmotion;

public interface BaiduSentimentAnalysis {
    DiaryEmotion sentimentAnalysis(String content);
}
