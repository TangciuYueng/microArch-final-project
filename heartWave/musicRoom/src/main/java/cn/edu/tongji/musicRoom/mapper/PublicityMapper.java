package cn.edu.tongji.musicRoom.mapper;

import cn.edu.tongji.musicRoom.model.Publicity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface PublicityMapper {
    void insertPublicityPublic(@Param("musicRoomId") int musicRoomId);
    void insertPublicityPrivate(Map<String, Objects> params);
    List<Publicity> getPublicityByMusicRoomId(@Param("musicRoomId") int musicRoomId);
}
