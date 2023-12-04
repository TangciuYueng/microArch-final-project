package cn.edu.tongji.Emotions.service;

import cn.edu.tongji.Emotions.model.DiaryEmotion;

public interface DiaryEmotionAnalysisService {
    DiaryEmotion sentimentAnalysis(String content);
}
