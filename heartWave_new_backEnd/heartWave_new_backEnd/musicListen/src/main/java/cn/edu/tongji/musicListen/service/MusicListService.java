package cn.edu.tongji.musicListen.service;

import cn.edu.tongji.musicListen.common.Result;
import cn.edu.tongji.musicListen.domain.MusicList;
import cn.edu.tongji.musicListen.dto.MusicListMedium;
import cn.edu.tongji.musicListen.dto.MusicListSimple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface MusicListService {
    Optional<MusicList> getMusicListById(Integer id);
    Page<MusicListSimple> findMusicListSimple(PageRequest pageRequest, String type, Integer userId);
    Page<MusicListMedium> findMusicListByName(PageRequest pageRequest, String name);
    void addMusicListToUser(Integer musicListId, Integer userId, String type);
    Page<Integer> getUserWhoTypeMusicList(Integer musicListId, String type, PageRequest pageRequest);
}
