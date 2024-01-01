package cn.edu.tongji.diaryWriting.service;


import cn.edu.tongji.diaryWriting.dto.DiaryEmotionDTO;

public interface BaiduSentimentAnalysis {
    DiaryEmotionDTO sentimentAnalysis(String content);
}
