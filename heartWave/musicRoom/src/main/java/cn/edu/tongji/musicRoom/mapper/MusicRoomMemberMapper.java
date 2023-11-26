package cn.edu.tongji.musicRoom.mapper;

import cn.edu.tongji.musicRoom.model.MusicRoomMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MusicRoomMemberMapper {
    void insertMusicRoomMember(@Param("userId") int userId, @Param("musicRoomId") int musicRoomId);

    void insertMusicRoomMemberByInstance(MusicRoomMember musicRoomMember);

    List<MusicRoomMember> getMusicRoomMemberByMusicRoomId(@Param("musicRoomId") int musicRoomId);

    int getMusicRoomIdById(@Param("memberId") int memberId);

    void setSingable(@Param("memberId") int memberId, @Param("singable") int singable);

    void setChatable(@Param("memberId") int memberId, @Param("chatable") int chatable);

    boolean getMemberChatable(@Param("userId") int userId, @Param("musicRoomId") int musicRoomId);

    void setStatus(@Param("memberId") int memberId, @Param("status") String status);

    int getCountMember(@Param("musicRoomId") int musicRoomId);
}
