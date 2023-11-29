package cn.edu.tongji.musicListen.service.impl;

import cn.edu.tongji.musicListen.mapper.MusicCommentMapper;
import cn.edu.tongji.musicListen.model.MusicComment;
import cn.edu.tongji.musicListen.service.MusicCommentService;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MusicCommentServiceImpl implements MusicCommentService {
    @Resource
    private MusicCommentMapper musicCommentMapper;

    @Override
    public int insertMusicComment(MusicComment musicComment) {
        return musicCommentMapper.insertMusicComment(musicComment);
    }

    //删除某一条评论(通过该评论的id)
    @Override
    public void deleteMusicComment(int id) {
        musicCommentMapper.deleteMusicComment(id);
    }

    // 通过音乐评论的id获取音乐评论
    @Override
    public MusicComment selectMusicCommentById(int id) {
        return musicCommentMapper.selectMusicCommentById(id);
    }

    // 获取这一首歌的评论数量
    @Override
    public int getThisMusicCommentCount(@Param("id") int musicId) {
        return musicCommentMapper.getThisMusicCommentCount(musicId);
    }

    // 获取某条评论的点赞数量
    @Override
    public int getCommentLikesCount(int id) {
        return musicCommentMapper.getCommentLikesCount(id);
    }

    // 为某条音乐评论增加val条点赞数
    @Override
    public ResponseEntity addCommentLikes(int id, int val) {
        musicCommentMapper.addCommentLikes(id, val);
        return null;
    }
}
