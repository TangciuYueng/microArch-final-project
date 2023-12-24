package cn.edu.tongji.diaryWriting.service;

import cn.edu.tongji.diaryWriting.model.DiaryEmotion;

public interface BaiduSentimentAnalysis {
    DiaryEmotion sentimentAnalysis(String content);
}
