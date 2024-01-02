package cn.edu.tongji.Emotions.repository;

import cn.edu.tongji.Emotions.model.CheckinEmotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CheckinEmotionRepository extends MongoRepository<CheckinEmotion, String> {
    // 定义特定于CheckinEmotion的方法
    Page<CheckinEmotion> findByCheckinTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay, Pageable pageable);
    List<CheckinEmotion> findByUserId(int userId);
    List<CheckinEmotion> findByCheckinTime(LocalDateTime startOfDay);
}
