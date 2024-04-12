package cn.edu.tongji.Emotions.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "music_emotion")
public class MusicEmotion {

    @Id
    private String id;
    private int musicId;
    private float dramatic;
    private float aggressive;
    private float peace;
    private float happy;
    private float sad;

}
