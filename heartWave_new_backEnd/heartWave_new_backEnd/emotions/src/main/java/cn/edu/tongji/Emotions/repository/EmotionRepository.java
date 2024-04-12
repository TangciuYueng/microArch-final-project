package cn.edu.tongji.Emotions.repository;

import cn.edu.tongji.Emotions.model.Emotion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmotionRepository extends MongoRepository<Emotion, String> {
    // 自定义方法（如果需要）
}