// UserSongEmotionRepository.java
package cn.edu.tongji.Emotions.repository;

import cn.edu.tongji.Emotions.model.UserSongEmotion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserSongEmotionRepository extends MongoRepository<UserSongEmotion, String> {
    // 自定义方法（如果需要）
}