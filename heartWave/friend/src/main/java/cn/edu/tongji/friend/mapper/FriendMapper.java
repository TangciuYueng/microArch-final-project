package cn.edu.tongji.friend.mapper;

import cn.edu.tongji.friend.model.Friend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FriendMapper {
    Friend getById(@Param("id") int id);
    Friend getByUserFriendId(@Param("userId") int userId, @Param("friendId") int friendId);
    List<Friend> getByUserId(@Param("userId") int userId);
    int add(Friend friend);
    int update(Friend friend);
    int deleteByUserFriendId(Friend friend);
}
