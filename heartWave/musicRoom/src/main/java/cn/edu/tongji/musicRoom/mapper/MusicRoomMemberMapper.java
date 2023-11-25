package cn.edu.tongji.musicRoom.mapper;

import cn.edu.tongji.musicRoom.model.MusicRoomMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MusicRoomMemberMapper {
    void insertMusicRoomMember(@Param("userId") int userId, @Param("musicRoomId") int musicRoomId);
    List<MusicRoomMember> getMusicRoomMemberByMusicRoomId(@Param("musicRoomId") int musicRoomId);
    void setStatusOut(@Param("userId") int userId, @Param("musicRoomId") int musicRoomId);
    void setStatusIn(@Param("userId") int userId, @Param("musicRoomId") int musicRoomId);
    void setSingable(@Param("userId") int userId, @Param("musicRoomId") int musicRoomId);
    void setNoSingable(@Param("userId") int userId, @Param("musicRoomId") int musicRoomId);
    void setChatable(@Param("userId") int userId, @Param("musicRoomId") int musicRoomId);
    void setNoChatable(@Param("userId") int userId, @Param("musicRoomId") int musicRoomId);
    void deleteMusicRoomMember(@Param("userId") int userId, @Param("musicRoomId") int musicRoomId);
}
