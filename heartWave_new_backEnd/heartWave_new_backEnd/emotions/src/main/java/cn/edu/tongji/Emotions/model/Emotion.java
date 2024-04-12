package cn.edu.tongji.Emotions.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "emotions")
public class Emotion {

    @Id
    private String id;
    private String userId;
    private LocalDateTime createTime;
    private float mood;

    // 无参构造函数
    public Emotion() {
    }

    // 带所有参数的构造函数
    public Emotion(String id, String userId, LocalDateTime createTime, float mood) {
        this.id = id;
        this.userId = userId;
        this.createTime = createTime;
        this.mood = mood;
    }

    @Override
    public String toString() {
        return "Emotion{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", createTime=" + createTime +
                ", mood='" + mood + '\'' +
                '}';
    }
}
