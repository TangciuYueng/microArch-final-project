package cn.edu.tongji.EmotionProcessor.client;


import cn.edu.tongji.EmotionProcessor.dto.DiaryEmotionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "emotion-service", path = "/api/emotions/diary-emotions")
public interface DiaryEmotionClient {
    ResponseEntity<?> createDiaryEmotion(@RequestBody DiaryEmotionDTO diaryEmotionDTO);
}
