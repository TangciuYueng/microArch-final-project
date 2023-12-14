package cn.edu.tongji.musicListen.service;

import cn.edu.tongji.musicListen.dto.MusicInfo;
import cn.edu.tongji.musicListen.dto.MusicRoomSongRequest;
import cn.edu.tongji.musicListen.mapper.MusicMapper;
import cn.edu.tongji.musicListen.model.Music;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public interface MusicService {

    //添加一首歌
    int insertMusic(Music music);
    //获取所有歌
    MusicInfo getAllMusic(int page);
    // 获取当天新增的歌曲
    List<Music> getNewMusicToday();
    //更新一首歌的信息
    void updateMusic(Music music);
    // 通过音乐id删除一首歌
    void deleteMusic(int id);
    // 通过音乐id获取音乐
    Music getMusicById(int id);
    //获取所有音乐的数量
    int getAllMusicCount();
    // 播放音乐
    int playMusic(int id);
    // 增加音乐室唱歌的记录
    @Transactional
    void addMusicRoomSong(MusicRoomSongRequest request);

    ResponseEntity<?> downloadAudio(String filePath) throws IOException;
}
