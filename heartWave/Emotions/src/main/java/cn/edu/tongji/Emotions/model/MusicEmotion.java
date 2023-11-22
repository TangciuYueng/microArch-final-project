package cn.edu.tongji.Emotions.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "music_emotion")
public class MusicEmotion {

    @Id
    private String id;
    private String musicId;
    private float dramatic;
    private float aggressive;
    private float romantic;
    private float happy;
    private float sad;

    // 构造函数
    public MusicEmotion() {
    }

    // 带所有参数的构造函数
    public MusicEmotion(String id, String musicId, float dramatic, float aggressive, float romantic, float happy, float sad) {
        this.id = id;
        this.musicId = musicId;
        this.dramatic = dramatic;
        this.aggressive = aggressive;
        this.romantic = romantic;
        this.happy = happy;
        this.sad = sad;
    }

    @Override
    public String toString() {
        return "MusicEmotion{" +
                "id='" + id + '\'' +
                ", musicId='" + musicId + '\'' +
                ", dramatic=" + dramatic +
                ", aggressive=" + aggressive +
                ", romantic=" + romantic +
                ", happy=" + happy +
                ", sad=" + sad +
                '}';
    }

}
