package cn.edu.tongji.diaryWriting.interfaces;

import cn.edu.tongji.Emotions.model.DiaryEmotion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "emotion-service", path = "/api/diaryEmotions")
public interface EmotionsServiceClient {
    @PostMapping
    ResponseEntity<?> createDiaryEmotion(@RequestBody DiaryEmotion diaryEmotion);
}
