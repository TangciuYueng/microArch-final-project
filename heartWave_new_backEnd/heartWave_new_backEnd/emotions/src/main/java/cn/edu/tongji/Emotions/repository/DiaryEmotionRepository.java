package cn.edu.tongji.Emotions.repository;

import cn.edu.tongji.Emotions.model.DiaryEmotion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DiaryEmotionRepository extends MongoRepository<DiaryEmotion, String> {
    // 定义特定于DiaryEmotion的方法
    Optional<DiaryEmotion> findByDiaryId(int diaryId);

    void deleteByDiaryId(int diaryId);

    List<DiaryEmotion> findAllByUserId(int userId);

    @Query("{'userId': ?0, 'createTime': {$gte: ?1, $lte: ?2}}")
    List<DiaryEmotion> findByUserIdAndCreateTimeBetween(int userId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
