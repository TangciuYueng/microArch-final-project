package cn.edu.tongji.musicListen.service;

import cn.edu.tongji.musicListen.domain.MusicList;
import cn.edu.tongji.musicListen.dto.MusicListMedium;
import cn.edu.tongji.musicListen.dto.MusicListSimple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface MusicListService {
    Optional<MusicList> getMusicListById(Integer id);
    Page<MusicListSimple> findMusicListSimple(PageRequest pageRequest, String type);
    Page<MusicListMedium> findMusicListByName(PageRequest pageRequest, String name);
}
