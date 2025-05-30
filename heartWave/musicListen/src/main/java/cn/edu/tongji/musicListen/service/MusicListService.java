package cn.edu.tongji.musicListen.service;


import cn.edu.tongji.musicListen.dto.MultiMusicMusicListRequest;
import cn.edu.tongji.musicListen.dto.MusicListRequest;
import cn.edu.tongji.musicListen.model.MusicList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service

public interface MusicListService {

    int insertMusicList(MusicListRequest request);

    Map<String, List<MusicList>> getMusicListByUserId(int userId);

    Map<String, List<MusicList>> getListenRecordListByUserId(int userId);

    List<Integer> insertMusicList(MultiMusicMusicListRequest request);
}
