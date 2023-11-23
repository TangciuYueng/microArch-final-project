package cn.edu.tongji.musicRoom.mapper;

import cn.edu.tongji.musicRoom.model.AdminGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminGroupMapper {
    //用于向数据库中插入一条AdminGroup记录
    void insertAdminGroup(@Param("userId") int userId, @Param("musicRoomId") int musicRoomId);
    //用于从数据库中删除一条AdminGroup记录
    void deleteAdminGroup(@Param("id") int id);
    //用于根据musicRoomId查询数据库中匹配的AdminGroup记录列表
    List<AdminGroup> getAdminGroupByMusicRoomId(@Param("musicRoomId") int musicRoomId);
}
