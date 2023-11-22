package cn.edu.tongji.musicListen.mapper;

import cn.edu.tongji.musicListen.model.PlayList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlayListMapper {
    int insertPlayList(PlayList playList);
    void updatePlayList(PlayList playList);
    void deletePlayList(int id);
    PlayList selectPlayListById(int id);
    int getAllPlayListCount();
    List<PlayList> getPlayListByPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
}
