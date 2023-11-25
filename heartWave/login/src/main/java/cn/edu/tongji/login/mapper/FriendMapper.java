package cn.edu.tongji.login.mapper;

import cn.edu.tongji.login.dto.AddFriendRequest;
import cn.edu.tongji.login.dto.DeleteFriendRequest;
import cn.edu.tongji.login.dto.FriendInfo;
import cn.edu.tongji.login.dto.UpdateFriendIntimacyRequest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FriendMapper {
    List<FriendInfo> getFriendByUserId(int userId);
    int addFriend(AddFriendRequest addFriendRequest);
    int updateFriendIntimacy(UpdateFriendIntimacyRequest updateFriendIntimacyRequest);
    int deleteFriend(DeleteFriendRequest deleteFriendRequest);
}
