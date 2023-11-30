package cn.edu.tongji.musicListen.dto;

import cn.edu.tongji.musicListen.model.Music;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MusicInfo {
    private List<Music> musics;
    private int page;
    private int totalPage;
}
