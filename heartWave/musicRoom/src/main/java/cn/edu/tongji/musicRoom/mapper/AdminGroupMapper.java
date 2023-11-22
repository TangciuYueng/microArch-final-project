package cn.edu.tongji.musicRoom.mapper;

import cn.edu.tongji.musicRoom.model.AdminGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminGroupMapper {
    void insertAdminGroup(@Param("userId") int userId, @Param("musicRoomId") int musicRoomId);

    void deleteAdminGroup(@Param("id") int id);

    List<AdminGroup> getAdminGroupByMusicRoomId(@Param("musicRoomId") int musicRoomId);
}
