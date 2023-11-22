// MusicEmotionRepository.java
package cn.edu.tongji.Emotions.repository;

import cn.edu.tongji.Emotions.model.MusicEmotion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MusicEmotionRepository extends MongoRepository<MusicEmotion, String> {
    // 自定义方法（如果需要）
}