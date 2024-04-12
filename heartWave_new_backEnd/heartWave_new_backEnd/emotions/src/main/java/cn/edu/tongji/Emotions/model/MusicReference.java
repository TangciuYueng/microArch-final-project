package cn.edu.tongji.Emotions.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
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
    private List<String> favoriteArtists;
    private LocalDateTime createTime; // 创建时间


}
