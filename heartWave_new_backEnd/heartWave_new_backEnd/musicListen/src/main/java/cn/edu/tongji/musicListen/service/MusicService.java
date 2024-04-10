package cn.edu.tongji.musicListen.service;

import cn.edu.tongji.musicListen.domain.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface MusicService {
    Optional<Music> getMusicById(Integer id);
    Page<Music> findAllByPage(PageRequest pageRequest);
    Page<Music> findMusicByTitle(PageRequest pageRequest, String title);
}
