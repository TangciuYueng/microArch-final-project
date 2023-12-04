package cn.edu.tongji.musicListen.service;


import cn.edu.tongji.musicListen.dto.MultiMusicMusicListRequest;
import cn.edu.tongji.musicListen.dto.MusicListId;
import cn.edu.tongji.musicListen.model.MusicList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service

public interface MusicListService {

    MusicListId insertMusicList(MusicList musicList);

    Map<String, List<MusicList>> getMusicListByUserId(int userId);

    Map<String, List<MusicList>> getListenRecordListByUserId(int userId);

    List<MusicListId> insertMusicList(MultiMusicMusicListRequest request);
}
