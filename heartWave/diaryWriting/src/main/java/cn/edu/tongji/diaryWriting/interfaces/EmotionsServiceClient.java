package cn.edu.tongji.diaryWriting.interfaces;


import cn.edu.tongji.diaryWriting.dto.DiaryEmotionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "emotion-service", path = "/api/emotions/diary-emotions")
public interface EmotionsServiceClient {
    @PostMapping
    ResponseEntity<?> createDiaryEmotion(@RequestBody DiaryEmotionDTO diaryEmotionDTO);
}
