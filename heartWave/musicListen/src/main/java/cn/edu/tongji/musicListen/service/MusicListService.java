package cn.edu.tongji.musicListen.service;


import cn.edu.tongji.musicListen.model.MusicList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service

public interface MusicListService {

    int insertMusicList(MusicList musicList);

    Map<String, List<MusicList>> getMusicListByUserId(int userId);
}
