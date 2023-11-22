package cn.edu.tongji.Emotions.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "diary_emotions")
public class DiaryEmotion {

    @Id
    private String id;
    private String userId;
    private String diaryId;
    private LocalDateTime createTime;
    private String sentiment;   // 可能的值例如 "positive", "neutral", "negative"
    private double confidence;  // 置信度分数，通常在0到1之间
    private double positive;    // 正面情绪得分
    private double negative;    // 负面情绪得分

    // 无参构造函数
    public DiaryEmotion() {
    }

    // 带所有参数的构造函数
    public DiaryEmotion(String id, String userId, String diaryId, LocalDateTime createTime, String sentiment, double confidence, double positive, double negative) {
        this.id = id;
        this.userId = userId;
        this.diaryId = diaryId;
        this.createTime = createTime;
        this.sentiment = sentiment;
        this.confidence = confidence;
        this.positive = positive;
        this.negative = negative;
    }

    @Override
    public String toString() {
        return "DiaryEmotion{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", diaryId='" + diaryId + '\'' +
                ", createTime=" + createTime +
                ", sentiment='" + sentiment + '\'' +
                ", confidence=" + confidence +
                ", positive=" + positive +
                ", negative=" + negative +
                '}';
    }
}
