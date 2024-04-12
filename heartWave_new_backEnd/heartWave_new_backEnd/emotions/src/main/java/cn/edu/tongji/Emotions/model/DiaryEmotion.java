package cn.edu.tongji.Emotions.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

/**
 * DiaryEmotion 实体表示用户日记相关的情绪数据。
 * 它存储于 "diary_emotions" 集合中，并包含了用户对特定日记的情绪评分。
 */
@Data
@Document(collection = "diary_emotions")
public class DiaryEmotion {

    @Id
    private String id; // 唯一标识符

    private int userId; // 用户ID，表示哪位用户的情绪数据

    private int diaryId; // 日记ID，表示这些情绪数据关联到的特定日记

    private LocalDateTime createTime; // 记录创建时间

    private float happy; // 表示快乐情绪的评分，范围0到1

    private float sad; // 表示悲伤情绪的评分，范围0到1

    private float aggressive; // 表示攻击性情绪的评分，范围0到1

    private float peace; // 表示平和情绪的评分，范围0到1

}
