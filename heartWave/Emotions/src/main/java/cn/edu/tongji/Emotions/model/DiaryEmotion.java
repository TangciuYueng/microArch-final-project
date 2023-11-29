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
    private int userId;
    private int diaryId;
    private LocalDateTime createTime;
    private int sentiment;
    private float confidence;  // 置信度分数，通常在0到1之间
    private float positive;    // 正面情绪得分
    private float negative;    // 负面情绪得分

    // 无参构造函数
    public DiaryEmotion() {
    }

    // 带所有参数的构造函数
    public DiaryEmotion(String id, int userId, int diaryId, LocalDateTime createTime, int sentiment, float confidence, float positive, float negative) {
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
