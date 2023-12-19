package cn.edu.tongji.friend.service.impl;

import cn.edu.tongji.friend.dto.AddChatRecordRequest;
import cn.edu.tongji.friend.dto.GetChatRecordPageByDateRequest;
import cn.edu.tongji.friend.dto.GetChatRecordPageByTimeRequest;
import cn.edu.tongji.friend.dto.GetChatRecordPageCountByDateRequest;
import cn.edu.tongji.friend.mapper.ChatRecordMapper;
import cn.edu.tongji.friend.model.ChatRecord;
import cn.edu.tongji.friend.service.ChatRecordService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatRecordServiceImpl implements ChatRecordService {
    @Resource
    private ChatRecordMapper chatRecordMapper;

    @Override
    @Transactional
    public ChatRecord addChatRecord(AddChatRecordRequest addChatRecordRequest) {
        ChatRecord chatRecord = ChatRecord.builder()
                .senderId(addChatRecordRequest.getSenderId())
                .receiverId(addChatRecordRequest.getReceiverId())
                .content(addChatRecordRequest.getContent())
                .type(addChatRecordRequest.getType())
                .build();

        chatRecordMapper.add(chatRecord);
        return chatRecordMapper.getById(chatRecord.getId());
    }

    @Override
    public void deleteChatRecord(int id) {
        chatRecordMapper.update(ChatRecord.builder()
                .id(id)
                .isDeleted(true)
                .build()
        );
    }

    @Override
    @Transactional
    public int getChatRecordPageCountByDate(GetChatRecordPageCountByDateRequest getChatRecordPageCountByDateRequest) {
        int chatRecordCount = chatRecordMapper.getCountByDate(
                getChatRecordPageCountByDateRequest.getSenderId(),
                getChatRecordPageCountByDateRequest.getReceiverId(),
                getChatRecordPageCountByDateRequest.getDate()
        );

        return (int) Math.ceil((double) chatRecordCount / 30);
    }

    @Override
    public List<ChatRecord> getChatRecordByPage(GetChatRecordPageByDateRequest getChatRecordPageByDateRequest) {
        return chatRecordMapper.getByPage(
                getChatRecordPageByDateRequest.getSenderId(),
                getChatRecordPageByDateRequest.getReceiverId(),
                getChatRecordPageByDateRequest.getDate(),
                getChatRecordPageByDateRequest.getPage() * 30
        );
    }

    @Override
    public List<ChatRecord> getChatRecordByTime(GetChatRecordPageByTimeRequest getChatRecordPageByTimeRequest) {
        return chatRecordMapper.getByTime(
                getChatRecordPageByTimeRequest.getSenderId(),
                getChatRecordPageByTimeRequest.getReceiverId(),
                getChatRecordPageByTimeRequest.getPage() * 30
        );
    }
}
