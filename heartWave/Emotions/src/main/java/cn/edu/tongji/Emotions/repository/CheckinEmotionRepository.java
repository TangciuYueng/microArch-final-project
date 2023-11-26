package cn.edu.tongji.Emotions.repository;

import cn.edu.tongji.Emotions.model.CheckinEmotion;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CheckinEmotionRepository extends MongoRepository<CheckinEmotion, String> {
    // 定义特定于CheckinEmotion的方法
    List<CheckinEmotion> findByCheckinTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
