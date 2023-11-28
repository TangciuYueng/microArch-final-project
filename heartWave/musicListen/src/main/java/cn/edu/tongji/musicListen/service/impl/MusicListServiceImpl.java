package cn.edu.tongji.musicListen.service.impl;

import cn.edu.tongji.musicListen.mapper.MusicListMapper;
import cn.edu.tongji.musicListen.model.MusicList;
import cn.edu.tongji.musicListen.service.MusicListService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MusicListServiceImpl implements MusicListService {
    @Resource
    private MusicListMapper musicListMapper;
    @Override
    public int insertMusicList(MusicList musicList){
        System.out.println(musicList);
        return musicListMapper.insertMusicList(musicList);
    }
}
