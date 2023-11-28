package cn.edu.tongji.musicListen.service;


import cn.edu.tongji.musicListen.mapper.MusicListMapper;
import cn.edu.tongji.musicListen.model.MusicList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service

public interface MusicListService {

    int insertMusicList(MusicList musicList);


}
