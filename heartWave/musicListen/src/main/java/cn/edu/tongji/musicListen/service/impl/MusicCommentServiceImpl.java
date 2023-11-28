package cn.edu.tongji.musicListen.service.impl;

import cn.edu.tongji.musicListen.model.Music;
import cn.edu.tongji.musicListen.model.MusicComment;
import cn.edu.tongji.musicListen.service.MusicCommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicCommentServiceImpl implements MusicCommentService {
    @Override
    public List<Music> getThisMusicComment(){
        return null;
    }

    @Override
    public int insertMusicComment(MusicComment musicComment) {
        return 0;
    }
}
