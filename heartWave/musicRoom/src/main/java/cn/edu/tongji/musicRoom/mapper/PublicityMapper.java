package cn.edu.tongji.musicRoom.mapper;

import cn.edu.tongji.musicRoom.model.Publicity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PublicityMapper {
    void insertPublicityPublic(@Param("musicRoomId") int musicRoomId);

    List<Publicity> getPublicityByMusicRoomId(@Param("musicRoomId") int musicRoomId);

    void insertPublicityLimited(@Param("userIdAndMusicRoomId") Map<Integer, Integer> userIdAndMusicRoomId);

    String getPublicityTypeByMusicRoomId(@Param("musicRoomId") int musicRoomId);
}
