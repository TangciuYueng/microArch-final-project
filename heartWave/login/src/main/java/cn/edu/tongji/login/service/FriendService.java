package cn.edu.tongji.login.service;

import cn.edu.tongji.login.dto.AddFriendRequest;
import cn.edu.tongji.login.dto.DeleteFriendRequest;
import cn.edu.tongji.login.dto.FriendInfo;
import cn.edu.tongji.login.dto.UpdateFriendIntimacyRequest;
import cn.edu.tongji.login.mapper.FriendMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendMapper friendMapper;

    public List<FriendInfo> getFriendByUserId(int userId) {
        return friendMapper.getFriendByUserId(userId);
    }

    @Transactional
    public boolean addFriend(int userId, int friendId) {
        try {
            friendMapper.addFriend(new AddFriendRequest(userId, friendId));
            friendMapper.addFriend(new AddFriendRequest(friendId, userId));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int updateFriendIntimacy(UpdateFriendIntimacyRequest updateFriendIntimacyRequest) {
        return friendMapper.updateFriendIntimacy(updateFriendIntimacyRequest);
    }

    @Transactional
    public boolean deleteFriend(int userId, int friendId) {
        try {
            friendMapper.deleteFriend(new DeleteFriendRequest(userId, friendId));
            friendMapper.deleteFriend(new DeleteFriendRequest(friendId, userId));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
