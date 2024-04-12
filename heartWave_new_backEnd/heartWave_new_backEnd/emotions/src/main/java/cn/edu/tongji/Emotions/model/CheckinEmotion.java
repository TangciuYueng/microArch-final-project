package cn.edu.tongji.Emotions.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Document(collection = "checkin_emotions")
public class CheckinEmotion {

    public enum EmotionType {
        AGGRESSIVE,
        HAPPY,
        SAD,
        PEACE
    }

    @Id
    private String id;
    private Integer userId;
    private LocalDateTime checkinTime;
    private Map<EmotionType, Double> emotions; // 使用枚举来存储情绪及其百分比
}