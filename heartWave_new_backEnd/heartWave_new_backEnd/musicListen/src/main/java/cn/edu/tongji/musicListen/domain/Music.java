package cn.edu.tongji.musicListen.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "music")
public class Music {
    @Id
    // 使用自增长策略生成主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String cover;
    private String type;
    private String writer;
    private String composer;
    private String producer;
    private String genre;
    private String artist;
    private String album; // 专辑
    @Column(name="release_date")
    private LocalDateTime releaseDate;
    private String src;
    private Integer duration;
    private String language;
    private Integer likes;
    private Integer downloads;
    private String lyrics; // 歌词
    private String label;
    @Column(name="play_count")
    private Integer playCount;
    @Column(name="music_mood")
    private String musicMood;
    @Column(name="storage_time")
    private LocalDateTime storageTime;
}
