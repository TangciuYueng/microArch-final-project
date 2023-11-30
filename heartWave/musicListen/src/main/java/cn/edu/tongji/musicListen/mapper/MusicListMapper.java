package cn.edu.tongji.musicListen.mapper;

import cn.edu.tongji.musicListen.model.MusicList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MusicListMapper {
    int insertMusicList(MusicList musicList);
    void updateMusicList(MusicList musicList);
    void deleteMusicList(int id);
    MusicList selectMusicListById(int id);
    int getAllMusicListCount();
    List<MusicList> getMusicListByPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
    List<MusicList> getMusicListByUserId(int userId);
}
