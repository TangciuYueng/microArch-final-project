package cn.edu.tongji.EmotionProcessor.client;

import cn.edu.tongji.EmotionProcessor.dto.DiaryEmotionDTO;
import cn.edu.tongji.EmotionProcessor.dto.MusicEmotionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "emotion-service", path = "/api/emotions")
public interface EmotionClient {

    @PostMapping("/diary-emotions")
    ResponseEntity<?> createDiaryEmotion(@RequestBody DiaryEmotionDTO diaryEmotionDTO);

    @PostMapping("/music-emotions")
    ResponseEntity<?> createMusicEmotion(@RequestBody MusicEmotionDTO musicEmotionDTO);
}
