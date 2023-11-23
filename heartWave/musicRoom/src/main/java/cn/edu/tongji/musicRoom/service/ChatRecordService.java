package cn.edu.tongji.musicRoom.service;

import cn.edu.tongji.musicRoom.dto.AddChatRecordRequest;
import cn.edu.tongji.musicRoom.dto.ChatRecordInfo;
import cn.edu.tongji.musicRoom.dto.MusicRoomInfo;
import cn.edu.tongji.musicRoom.mapper.ChatRecordMapper;
import cn.edu.tongji.musicRoom.model.ChatRecord;
import cn.edu.tongji.musicRoom.model.MusicRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
//用来实现聊天的业务逻辑
public class ChatRecordService {
    private final ChatRecordMapper chatRecordMapper;
    private final static int PAGE_SIZE = 30;

   //通过调用ChatRecordMapper的方法获取指定音乐房间ID下的聊天记录列表，
   // 并通过ChatRecordInfo对象封装返回结果，包括分页后的聊天记录列表，总页数和当前页码。
    public ChatRecordInfo getAllChatRecord(int page, Integer musicRoomId) {
        int startIndex = (page - 1) * PAGE_SIZE;
        List<ChatRecord> chatRecords = chatRecordMapper.getChatRecordByPage(startIndex, PAGE_SIZE, musicRoomId);
        //获取所有聊天记录
        int totalMusicRoom = chatRecordMapper.getAllChatRecord();
        int totalPage = (int) Math.ceil((double) totalMusicRoom / PAGE_SIZE);

        return new ChatRecordInfo(chatRecords, totalPage, page);
    }

    public void deleteChatRecord(int chatRecordId, boolean isDeleted) {
        chatRecordMapper.deleteChatRecord(chatRecordId, isDeleted? 1: 0);
    }

    public int addChatRecord(AddChatRecordRequest addChatRecordRequest) {
        chatRecordMapper.insertChatRecord(addChatRecordRequest);
        return addChatRecordRequest.getId();
    }
}
