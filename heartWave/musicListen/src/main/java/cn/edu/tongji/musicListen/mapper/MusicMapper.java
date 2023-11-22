package cn.edu.tongji.musicListen.mapper;

import cn.edu.tongji.musicListen.model.Music;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MusicMapper {
    int insertMusic(Music music);
    void updateMusic(Music music);
    void deleteMusic(int id);
    Music selectMusicById(int id);
    int getAllMusicCount();
    List<Music> getMusicByPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
}
