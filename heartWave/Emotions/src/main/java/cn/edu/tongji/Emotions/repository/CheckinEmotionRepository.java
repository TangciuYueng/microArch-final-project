package cn.edu.tongji.Emotions.repository;

import cn.edu.tongji.Emotions.model.CheckinEmotion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CheckinEmotionRepository extends MongoRepository<CheckinEmotion, String> {
    // 定义特定于CheckinEmotion的方法
}
