package cn.edu.tongji.friend.service.impl;

import cn.edu.tongji.friend.dto.UpdateChatTimeRequest;
import cn.edu.tongji.friend.model.FriendRelation;
import cn.edu.tongji.friend.repository.FriendRelationRepository;
import cn.edu.tongji.friend.repository.FriendRepository;
import cn.edu.tongji.friend.service.ChatService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChatServiceImpl implements ChatService {
    @Resource
    private FriendRepository friendRepository;

    @Resource
    private FriendRelationRepository friendRelationRepository;

    @Override
    public void updateChatTime(UpdateChatTimeRequest updateChatTimeRequest) {
        FriendRelation friendRelation = friendRelationRepository.findFirstByUserIdAndFriendId(updateChatTimeRequest.getUserId1(), updateChatTimeRequest.getUserId2());
        friendRepository.updateTimeById(friendRelation.getFriend().getId(), LocalDateTime.now());
    }
}
