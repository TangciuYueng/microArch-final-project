package cn.edu.tongji.friend.service.impl;

import cn.edu.tongji.friend.dto.AddChatRecordRequest;
import cn.edu.tongji.friend.model.ChatRecordType;
import cn.edu.tongji.friend.model.FriendRelation;
import cn.edu.tongji.friend.model.RedisChatRecord;
import cn.edu.tongji.friend.repository.FriendRelationRepository;
import cn.edu.tongji.friend.repository.FriendRepository;
import cn.edu.tongji.friend.repository.RedisChatRecordRepository;
import cn.edu.tongji.friend.service.COSService;
import cn.edu.tongji.friend.service.ChatService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    @Resource
    private RedisChatRecordRepository redisChatRecordRepository;
    @Resource
    private FriendRepository friendRepository;
    @Resource
    private FriendRelationRepository friendRelationRepository;
    @Resource
    private COSService cosService;
    @Resource
    private DateTimeFormatter timeFormatter;

    @Override
    public void addChatRecord(AddChatRecordRequest addChatRecordRequest) {
        RedisChatRecord redisChatRecord = RedisChatRecord.builder()
                .senderId(addChatRecordRequest.getSenderId())
                .receiverId(addChatRecordRequest.getReceiverId())
                .sendTime(LocalDateTime.now().format(timeFormatter))
                .type(addChatRecordRequest.getType())
                .build();

        switch (addChatRecordRequest.getType()) {
            case TEXT, URL -> {
                redisChatRecord.setContent(addChatRecordRequest.getContent());
                redisChatRecordRepository.save(redisChatRecord);
            }
            case PIC -> {
                byte[] imageBytes = Base64.getDecoder().decode(addChatRecordRequest.getContent());
                redisChatRecordRepository.save(redisChatRecord);
                cosService.uploadFileBytes(imageBytes, "chatRecord/single/" + redisChatRecord.getId() + ".jpg");
            }
        }

        FriendRelation friendRelation = friendRelationRepository.findFirstByUserIdAndFriendId(
                addChatRecordRequest.getSenderId(),
                addChatRecordRequest.getReceiverId()
        );
        friendRepository.updateTimeById(friendRelation.getFriend().getId(), LocalDateTime.now());
    }

    @Override
    public List<RedisChatRecord> getChatRecord(long uid1, long uid2) throws IOException {
        List<RedisChatRecord> chatRecords = redisChatRecordRepository.get(uid1, uid2);

        for (RedisChatRecord record : chatRecords) {
            if (record.getType() == ChatRecordType.PIC)
                record.setContent(cosService.getChatImage(record.getId()));
        }

        return chatRecords;
    }
}
