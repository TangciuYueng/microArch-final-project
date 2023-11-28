package cn.edu.tongji.musicListen.service;

import cn.edu.tongji.musicListen.mapper.MusicMapper;
import cn.edu.tongji.musicListen.model.Music;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public interface MusicService {

    int insertMusic(Music music);
    List<Music> getAllMusic();
    void updateMusic(Music music);

    void deleteMusic(int id);
    // 播放音乐
    void playMusic(int id);
}
