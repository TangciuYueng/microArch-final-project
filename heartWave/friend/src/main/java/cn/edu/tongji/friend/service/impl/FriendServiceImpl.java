package cn.edu.tongji.friend.service.impl;

import cn.edu.tongji.friend.dto.FriendInfo;
import cn.edu.tongji.friend.dto.UpdateFriendIntimacyRequest;
import cn.edu.tongji.friend.interfaces.LoginServiceClient;
import cn.edu.tongji.friend.mapper.FriendMapper;
import cn.edu.tongji.friend.model.Friend;
import cn.edu.tongji.friend.model.User;
import cn.edu.tongji.friend.service.FriendService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {
    @Resource
    private FriendMapper friendMapper;
    @Resource
    private LoginServiceClient loginServiceClient;

    @Override
    @Transactional
    public List<FriendInfo> getFriendsByUserId(int userId) {
        List<FriendInfo> friendInfos = new ArrayList<>();
        List<Friend> friends = friendMapper.getByUserId(userId);

        for (Friend friend: friends) {
            User user = (User) loginServiceClient.getUserById(friend.getFriendId()).getBody();

            friendInfos.add(new FriendInfo(
                    user.getId(),
                    user.getName(),
                    user.getAvatar(),
                    friend.getIntimacy()
            ));
        }

        return friendInfos;
    }

    @Override
    @Transactional
    public List<Friend> addFriend(int userId, int friendId) {
        List<Friend> friends = new ArrayList<>();

        Friend friend1 = Friend.builder()
                .userId(userId)
                .friendId(friendId)
                .build();
        Friend friend2 = Friend.builder()
                .userId(friendId)
                .friendId(userId)
                .build();

        friendMapper.add(friend1);
        friendMapper.add(friend2);
        friends.add(friendMapper.getById(friend1.getId()));
        friends.add(friendMapper.getById(friend2.getId()));
        return friends;
    }

    @Override
    public double updateFriendIntimacy(UpdateFriendIntimacyRequest updateFriendIntimacyRequest) {
        Friend friend = friendMapper.getByUserFriendId(updateFriendIntimacyRequest.getUserId(), updateFriendIntimacyRequest.getFriendId());
        double newIntimacy = friend.getIntimacy() + updateFriendIntimacyRequest.getVariation();

        //将亲密度控制在0-100之间，避免使用触发器降低性能
        if (newIntimacy > 100)
            friend.setIntimacy(100.0);
        else if (newIntimacy < 0)
            friend.setIntimacy(0.0);
        else
            friend.setIntimacy(newIntimacy);

        friendMapper.update(friend);
        return newIntimacy;
    }

    @Override
    @Transactional
    public void deleteFriend(int userId, int friendId) {
        friendMapper.deleteByUserFriendId(Friend.builder()
                .userId(userId)
                .friendId(friendId)
                .build()
        );
        friendMapper.deleteByUserFriendId(Friend.builder()
                .userId(friendId)
                .friendId(userId)
                .build()
        );
    }
}
