package cn.edu.tongji.Emotions.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "checkin_emotions")
public class CheckinEmotion {

    @Id
    private String id;
    private int userId;
    private LocalDateTime checkinTime;
    private double emotionValue; // 假设这是一个能够表示情绪的数值

    // 无参构造函数
    public CheckinEmotion() {
    }

    // 带所有参数的构造函数
    public CheckinEmotion(String id, int userId, LocalDateTime checkinTime, double emotionValue) {
        this.id = id;
        this.userId = userId;
        this.checkinTime = checkinTime;
        this.emotionValue = emotionValue;
    }

    @Override
    public String toString() {
        return "CheckinEmotion{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", checkinTime=" + checkinTime +
                ", emotionValue=" + emotionValue +
                '}';
    }
}
