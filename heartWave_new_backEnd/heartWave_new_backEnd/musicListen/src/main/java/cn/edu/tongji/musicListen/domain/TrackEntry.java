package cn.edu.tongji.musicListen.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class TrackEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musicListId", nullable = false)
    private MusicList musicList; // 所属的MusicList

    private Integer musicId; // 音乐ID
    private LocalDateTime latestTime; // 加入歌单的时间
    private Integer listOrder; // 在歌单中的顺序
}
