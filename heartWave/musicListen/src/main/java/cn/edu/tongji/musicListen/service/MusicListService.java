package cn.edu.tongji.musicListen.service;


import cn.edu.tongji.musicListen.mapper.MusicListMapper;
import cn.edu.tongji.musicListen.model.MusicList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MusicListService {
    private final MusicListMapper musicListMapper;

    public int insertMusicList(MusicList musicList) {
        System.out.println(musicList);
        return musicListMapper.insertMusicList(musicList);
    }


}
