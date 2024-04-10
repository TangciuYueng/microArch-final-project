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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "cover")
    private String cover;

    @Column(name = "type")
    private String type;

    @Column(name = "writer")
    private String writer;

    @Column(name = "composer")
    private String composer;

    @Column(name = "producer")
    private String producer;

    @Column(name = "genre")
    private String genre;

    @Column(name = "artist")
    private String artist;

    @Column(name = "album")
    private String album;

    @Column(name = "release_date")
    private LocalDateTime releaseDate;

    @Column(name = "src")
    private String src;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "language")
    private String language;

    @Column(name = "likes")
    private Integer likes;

    @Column(name = "downloads")
    private Integer downloads;

    @Column(name = "lyrics")
    private String lyrics;

    @Column(name = "label")
    private String label;

    @Column(name = "play_count")
    private Integer playCount;

    @Column(name = "music_mood")
    private String musicMood;

    @Column(name = "storage_time")
    private LocalDateTime storageTime;
}

