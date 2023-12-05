package cn.edu.tongji.EmotionProcessor.service;
import org.springframework.http.ResponseEntity;

public interface DiaryEmotionAnalysisService {
    ResponseEntity<?> sentimentAnalysis(String content);
}
