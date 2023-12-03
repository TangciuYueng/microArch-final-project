package cn.edu.tongji.Emotions.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Document(collection = "music_references")
public class MusicReference {
    public enum Genres {
        POP,
        ROCK,
        JAZZ,
        CLASSICAL,
        ELECTRONIC,
        HIP_HOP,
        RNB_SOUL,
        COUNTRY,
        LATIN,
        METAL,
        BLUES,
        FOLK,
        WORLD_MUSIC,
        INDIE,
        INSTRUMENTAL;
    }

    @Id
    private String id;
    private String userId;
    private Map<Genres, Double> genre; // 音乐类型
    private LocalDateTime createTime; // 创建时间

    // 无参构造函数
    public MusicReference() {
    }

    // 带所有参数的构造函数
    public MusicReference(String id, String userId, Map<Genres, Double> genre, LocalDateTime createTime) {
        this.id = id;
        this.userId = userId;
        this.genre = genre;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "MusicReference{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", genre='" + genre + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
