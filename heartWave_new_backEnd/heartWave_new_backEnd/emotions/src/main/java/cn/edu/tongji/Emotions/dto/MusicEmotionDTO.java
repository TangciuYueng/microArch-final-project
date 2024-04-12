package cn.edu.tongji.Emotions.dto;

import lombok.Data;

@Data
public class MusicEmotionDTO {

    private String id;
    private int musicId;
    private float dramatic;
    private float aggressive;
    private float peace;
    private float happy;
    private float sad;

}
