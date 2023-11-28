package cn.edu.tongji.musicListen.mapper;

import cn.edu.tongji.musicListen.model.Music;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
@Mapper
public interface MusicMapper {
    //插入一首歌进总乐曲库
    int insertMusic(Music music);
    //在总乐库中更新一首歌
    void updateMusic(Music music);
    //从总乐库删一首歌
    void deleteMusic(int id);
    //通过音乐id获取这首歌的信息
    Music selectMusicById(int id);
    //获取所有音乐的数量
    int getAllMusicCount();
    //获取所有音乐
    List<Music> getAllMusic();
    //获取部分音乐
    List<Music> getMusicByPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
}
