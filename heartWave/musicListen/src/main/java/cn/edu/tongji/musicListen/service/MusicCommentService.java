package cn.edu.tongji.musicListen.service;

import cn.edu.tongji.musicListen.model.MusicComment;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface MusicCommentService {



    int insertMusicComment(MusicComment musicComment);

    //删除某一条评论(通过该评论的id)
    void deleteMusicComment(int id);
    // 通过音乐评论的id获取音乐评论
    MusicComment selectMusicCommentById(int id);
    // 获取这一首歌的评论数量
    int getThisMusicCommentCount(@Param("id") int musicId);
    // 获取某条评论的点赞数量
    int getCommentLikesCount(int id);
    // 为某条音乐评论增加val条点赞数
    ResponseEntity addCommentLikes(int id, int val);
}
