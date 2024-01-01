package cn.edu.tongji.EmotionProcessor.client;

import cn.edu.tongji.Emotions.dto.MusicEmotionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "emotion-service", path = "/api/emotions/music-emotions")
public interface EmotionsClient {
    ResponseEntity<?> createMusicEmotion(@RequestBody MusicEmotionDTO musicEmotionDTO);
}
