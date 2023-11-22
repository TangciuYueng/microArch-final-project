package cn.edu.tongji.musicListen.mapper;

import cn.edu.tongji.musicListen.model.MusicComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MusicCommentMapper {
    int insertMusicComment(MusicComment musicComment);
    void updateMusicComment(MusicComment musicComment);
    void deleteMusicComment(int id);
    MusicComment selectMusicCommentById(int id);
    int getAllMusicCommentCount();
    List<MusicComment> getMusicCommentByPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
}
