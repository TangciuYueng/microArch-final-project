package cn.edu.tongji.friend.service.impl;

import cn.edu.tongji.friend.dto.AddChatRecordRequest;
import cn.edu.tongji.friend.dto.UpdateChatTimeRequest;
import cn.edu.tongji.friend.model.FriendRelation;
import cn.edu.tongji.friend.model.RedisChatRecord;
import cn.edu.tongji.friend.repository.ChatRecordRepository;
import cn.edu.tongji.friend.repository.FriendRelationRepository;
import cn.edu.tongji.friend.repository.FriendRepository;
import cn.edu.tongji.friend.service.ChatService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChatServiceImpl implements ChatService {
    @Resource
    private ChatRecordRepository chatRecordRepository;
    @Resource
    private FriendRepository friendRepository;
    @Resource
    private FriendRelationRepository friendRelationRepository;

    @Override
    public void addChatRecord(AddChatRecordRequest addChatRecordRequest) {
        RedisChatRecord redisChatRecord = RedisChatRecord.builder()
                        .senderId(addChatRecordRequest.getSenderId())
                        .receiverId(addChatRecordRequest.getReceiverId())
                        .sendTime(LocalDateTime.now())
                        .type(addChatRecordRequest.getType())
                        .content(addChatRecordRequest.getContent())
                        .build();

        chatRecordRepository.save(redisChatRecord);
    }

    @Override
    public void updateChatTime(UpdateChatTimeRequest updateChatTimeRequest) {
        FriendRelation friendRelation = friendRelationRepository.findFirstByUserIdAndFriendId(updateChatTimeRequest.getUserId1(), updateChatTimeRequest.getUserId2());
        friendRepository.updateTimeById(friendRelation.getFriend().getId(), LocalDateTime.now());
    }
}
