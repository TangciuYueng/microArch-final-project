// MusicEmotionRepository.java
package cn.edu.tongji.Emotions.repository;

import cn.edu.tongji.Emotions.model.MusicEmotion;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MusicEmotionRepository extends MongoRepository<MusicEmotion, String> {
    Optional<MusicEmotion> findByMusicId(int musicId);
}