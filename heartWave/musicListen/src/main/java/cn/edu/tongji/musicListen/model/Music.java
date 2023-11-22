package cn.edu.tongji.musicListen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Music {
    private int id;
    private String title;
    private String type;
    private String writer;
    private String composer;
    private String producer;
    private String genre;
    private String artist;
    private String album; // 专辑
    private LocalDate release_date;
    private String src;
    private int duration;
    private String language;
    private int likes;
    private int downloads;
    private String lyrics; // 歌词
    private String label;
    private int play_count;
    private String music_mood;
}
