package cn.edu.tongji.musicListen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class COSMusicAddRequest {
    private String localPath;
    private String cosPath;

    private String title;
    private String type;
    private String writer;
    private String composer;
    private String producer;
    private String genre;
    private String artist;
    private String album; // 专辑
    private LocalDateTime releaseDate;
    private String src;
    private int duration;
    private String language;
    private int likes;
    private int downloads;
    private String lyrics; // 歌词
    private String label;
    private int playCount;
    private String musicMood;
    private LocalDateTime storageTime;
}
