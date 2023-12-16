package cn.edu.tongji.friend.service;

import cn.edu.tongji.friend.dto.FriendInfo;
import cn.edu.tongji.friend.dto.UpdateFriendIntimacyRequest;
import cn.edu.tongji.friend.model.Friend;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FriendService {
    List<FriendInfo> getFriendsByUserId(int userId);
    List<Friend> addFriend(int userId, int friendId);
    double updateFriendIntimacy(UpdateFriendIntimacyRequest updateFriendIntimacyRequest);
    void deleteFriend(int userId, int friendId);
}
