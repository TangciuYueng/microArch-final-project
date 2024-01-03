package cn.edu.tongji.diaryWriting.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DiaryEmotionDTO {

    // Getter 和 Setter 方法
    private String id;
    private int userId;
    private int diaryId;
    private LocalDateTime createTime;
    private int sentiment;
    private float confidence;  // 置信度分数
    private float positive;    // 正面情绪得分
    private float negative;    // 负面情绪得分

    // 无参构造函数
    public DiaryEmotionDTO() {
    }

    // 带所有参数的构造函数
    public DiaryEmotionDTO(String id, int userId, int diaryId, LocalDateTime createTime, int sentiment, float confidence, float positive, float negative) {
        this.id = id;
        this.userId = userId;
        this.diaryId = diaryId;
        this.createTime = createTime;
        this.sentiment = sentiment;
        this.confidence = confidence;
        this.positive = positive;
        this.negative = negative;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDiaryId(int diaryId) {
        this.diaryId = diaryId;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public void setSentiment(int sentiment) {
        this.sentiment = sentiment;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }

    public void setPositive(float positive) {
        this.positive = positive;
    }

    public void setNegative(float negative) {
        this.negative = negative;
    }

    @Override
    public String toString() {
        return "DiaryEmotionDTO{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", diaryId=" + diaryId +
                ", createTime=" + createTime +
                ", sentiment=" + sentiment +
                ", confidence=" + confidence +
                ", positive=" + positive +
                ", negative=" + negative +
                '}';
    }
}
