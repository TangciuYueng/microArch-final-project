package cn.edu.tongji.Emotions.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "user_song_emotion")
public class UserSongEmotion {

    @Id
    private String id;
    private String userId;
    private String musicId;
    private float dramatic;
    private float aggressive;
    private float romantic;
    private float happy;
    private float sad;
    private LocalDateTime createTime;

    // 构造函数
    public UserSongEmotion() {
    }

    // 带所有参数的构造函数
    public UserSongEmotion(String id, String userId, String musicId, float dramatic, float aggressive, float romantic, float happy, float sad, LocalDateTime createTime) {
        this.id = id;
        this.userId = userId;
        this.musicId = musicId;
        this.dramatic = dramatic;
        this.aggressive = aggressive;
        this.romantic = romantic;
        this.happy = happy;
        this.sad = sad;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserSongEmotion{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", musicId='" + musicId + '\'' +
                ", dramatic=" + dramatic +
                ", aggressive=" + aggressive +
                ", romantic=" + romantic +
                ", happy=" + happy +
                ", sad=" + sad +
                ", createTime=" + createTime +
                '}';
    }
}
