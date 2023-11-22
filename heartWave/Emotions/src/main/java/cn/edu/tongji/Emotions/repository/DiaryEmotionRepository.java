package cn.edu.tongji.Emotions.repository;

import cn.edu.tongji.Emotions.model.DiaryEmotion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DiaryEmotionRepository extends MongoRepository<DiaryEmotion, String> {
    // 定义特定于DiaryEmotion的方法
}
