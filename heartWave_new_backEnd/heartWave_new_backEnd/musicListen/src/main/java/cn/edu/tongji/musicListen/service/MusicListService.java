package cn.edu.tongji.musicListen.service;

import cn.edu.tongji.musicListen.domain.MusicList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface MusicListService {
    Optional<MusicList> getMusicListById(Integer id);
    Page<MusicList> findAdminMusicLists(PageRequest pageRequest);
}
