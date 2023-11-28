package cn.edu.tongji.musicListen.service;

import cn.edu.tongji.musicListen.model.Music;

import cn.edu.tongji.musicListen.model.MusicComment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MusicCommentService {

    List<Music> getThisMusicComment();

    int insertMusicComment(MusicComment musicComment);
}
