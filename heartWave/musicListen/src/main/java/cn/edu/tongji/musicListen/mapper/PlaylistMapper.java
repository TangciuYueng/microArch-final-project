package cn.edu.tongji.musicListen.mapper;

import cn.edu.tongji.musicListen.model.Playlist;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlaylistMapper {
    int insertPlaylist(Playlist playList);
    void updatePlaylist(Playlist playList);
    void deletePlaylist(int id);
    Playlist selectPlaylistById(int id);
    int getAllPlaylistCount();
    List<Playlist> getAllPlaylist();
    List<Playlist> getPlaylistByPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
}
