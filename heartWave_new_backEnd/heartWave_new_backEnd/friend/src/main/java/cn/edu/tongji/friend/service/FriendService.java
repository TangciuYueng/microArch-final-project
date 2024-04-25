package cn.edu.tongji.friend.service;

import cn.edu.tongji.friend.dto.AddFriendRequest;
import cn.edu.tongji.friend.dto.FriendInfo;

import java.io.IOException;
import java.util.List;

public interface FriendService {
    void addFriend(AddFriendRequest addFriendRequest);
    List<FriendInfo> getFriends(Long userId) throws IOException;
}
