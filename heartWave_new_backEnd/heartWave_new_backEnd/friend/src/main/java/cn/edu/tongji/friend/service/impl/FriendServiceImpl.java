package cn.edu.tongji.friend.service.impl;

import cn.edu.tongji.friend.dto.AddFriendRequest;
import cn.edu.tongji.friend.dto.FriendInfo;
import cn.edu.tongji.friend.dto.UserFriendInfo;
import cn.edu.tongji.friend.model.Friend;
import cn.edu.tongji.friend.model.FriendRelation;
import cn.edu.tongji.friend.repository.FriendRelationRepository;
import cn.edu.tongji.friend.repository.FriendRepository;
import cn.edu.tongji.friend.service.COSService;
import cn.edu.tongji.friend.service.FriendService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {
    @Resource
    private FriendRepository friendRepository;
    @Resource
    private FriendRelationRepository friendRelationRepository;
    @Resource
    private COSService cosService;

    @Override
    public void addFriend(AddFriendRequest addFriendRequest) {
        FriendRelation relation1 = FriendRelation.builder()
                .userId(addFriendRequest.getUserId1())
                .friendId(addFriendRequest.getUserId2())
                .intimacy(0.0)
                .build();

        FriendRelation relation2 = FriendRelation.builder()
                .userId(addFriendRequest.getUserId2())
                .friendId(addFriendRequest.getUserId1())
                .intimacy(0.0)
                .build();

        Friend friend = Friend.builder()
                .chatTime(LocalDateTime.now())
                .build();

        friendRepository.save(friend);
        relation1.setFriend(friend);
        relation2.setFriend(friend);
        friendRelationRepository.save(relation1);
        friendRelationRepository.save(relation2);
    }

    @Override
    public List<Long> getFriendIds(Long userId) {
        List<Long> friendIds = new ArrayList<>();  //好友id列表

        //查单向关系表，得到所有单向关系
        List<FriendRelation> relations = friendRelationRepository.findByUserId(userId);

        //将各个好友id加入List
        for (FriendRelation relation : relations) {
            friendIds.add(relation.getFriendId());
        }

        return friendIds;
    }

    @Override
    public List<FriendInfo> getFriends(Long userId, List<UserFriendInfo> userFriendInfos) throws IOException {
        List<Long> friendIds = getFriendIds(userId);
        List<FriendInfo> ret = new ArrayList<>();  //返回值列表
        List<Double> intimacies = new ArrayList<>();  //亲密度列表

        //查单向关系表，得到所有单向关系
        List<FriendRelation> relations = friendRelationRepository.findByUserId(userId);

        //把返回值列表每一个元素的intimacy属性记录
        for (FriendRelation relation : relations) {
            intimacies.add(relation.getIntimacy());
        }

        for (int i = 0; i < friendIds.size(); i++) {
            //根据单向关系得到双向关系id，并得到双向关系
            Friend friend = friendRepository.findFirstById(relations.get(i).getFriend().getId());

            //将所有信息拼接到FriendInfo对象中，并加入返回列表
            ret.add(new FriendInfo(
                    friendIds.get(i),
                    intimacies.get(i),
                    friend.getChatTime(),
                    userFriendInfos.get(i).getName(),
                    cosService.getUserAvatar(friendIds.get(i))
            ));
        }

        return ret;
    }
}
