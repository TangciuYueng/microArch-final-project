package cn.edu.tongji.friend.service;

import cn.edu.tongji.friend.dto.AddFriendRequest;
import cn.edu.tongji.friend.dto.FriendInfo;
import cn.edu.tongji.friend.dto.UserFriendInfo;

import java.io.IOException;
import java.util.List;

public interface FriendService {
    void addFriend(AddFriendRequest addFriendRequest);
    List<Long> getFriendIds(Long userId);
    List<FriendInfo> getFriends(Long userId, List<UserFriendInfo> userFriendInfos) throws IOException;
}
