package cn.edu.tongji.Emotions.dto;

public class MusicEmotionDTO {

    private String id;
    private int musicId;
    private float dramatic;
    private float aggressive;
    private float romantic;
    private float happy;
    private float sad;

    // 构造函数
    public MusicEmotionDTO() {
    }

    // 带所有参数的构造函数
    public MusicEmotionDTO(String id, int musicId, float dramatic, float aggressive, float romantic, float happy, float sad) {
        this.id = id;
        this.musicId = musicId;
        this.dramatic = dramatic;
        this.aggressive = aggressive;
        this.romantic = romantic;
        this.happy = happy;
        this.sad = sad;
    }

    // getter和setter方法
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMusicId() {
        return musicId;
    }

    public void setMusicId(int musicId) {
        this.musicId = musicId;
    }

    public float getDramatic() {
        return dramatic;
    }

    public void setDramatic(float dramatic) {
        this.dramatic = dramatic;
    }

    public float getAggressive() {
        return aggressive;
    }

    public void setAggressive(float aggressive) {
        this.aggressive = aggressive;
    }

    public float getRomantic() {
        return romantic;
    }

    public void setRomantic(float romantic) {
        this.romantic = romantic;
    }

    public float getHappy() {
        return happy;
    }

    public void setHappy(float happy) {
        this.happy = happy;
    }

    public float getSad() {
        return sad;
    }

    public void setSad(float sad) {
        this.sad = sad;
    }

    // toString方法
    @Override
    public String toString() {
        return "MusicEmotionDTO{" +
                "id='" + id + '\'' +
                ", musicId=" + musicId +
                ", dramatic=" + dramatic +
                ", aggressive=" + aggressive +
                ", romantic=" + romantic +
                ", happy=" + happy +
                ", sad=" + sad +
                '}';
    }
}
