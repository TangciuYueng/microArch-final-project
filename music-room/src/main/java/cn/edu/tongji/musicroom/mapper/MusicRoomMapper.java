package cn.edu.tongji.musicroom.mapper;

import cn.edu.tongji.musicroom.model.MusicRoom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MusicRoomMapper {
    int insertMusicRoom(MusicRoom musicRoom);
    void updateMusicRoom(MusicRoom musicRoom);
    void deleteMusicRoom(int id);
    MusicRoom selectMusicRoomById(int id);
    int getAllMusicRoom();
    List<MusicRoom> getMusicRoomByPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
}
