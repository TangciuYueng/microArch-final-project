package cn.edu.tongji.musicRoom.service;

import cn.edu.tongji.musicRoom.dto.AddChatRecordRequest;
import cn.edu.tongji.musicRoom.dto.ChatRecordInfo;
import cn.edu.tongji.musicRoom.mapper.AdminGroupMapper;
import cn.edu.tongji.musicRoom.mapper.ChatRecordMapper;
import cn.edu.tongji.musicRoom.mapper.MusicRoomMemberMapper;
import cn.edu.tongji.musicRoom.model.AdminGroup;
import cn.edu.tongji.musicRoom.model.ChatRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
//用来实现聊天的业务逻辑
public class ChatRecordService {
    private final ChatRecordMapper chatRecordMapper;
    private final MusicRoomMemberMapper musicRoomMemberMapper;
    private final AdminGroupMapper adminGroupMapper;
    private final static int PAGE_SIZE = 30;

    // 通过调用ChatRecordMapper的方法获取指定音乐房间ID下的聊天记录列表，
    // 并通过ChatRecordInfo对象封装返回结果，包括分页后的聊天记录列表，总页数和当前页码。
    public ChatRecordInfo getAllChatRecord(int page, Integer musicRoomId) {
        int startIndex = (page - 1) * PAGE_SIZE;
        List<ChatRecord> chatRecords = chatRecordMapper.getChatRecordByPage(startIndex, PAGE_SIZE, musicRoomId);
        // 获取所有聊天记录
        int totalMusicRoom = chatRecordMapper.getAllChatRecord();
        int totalPage = (int) Math.ceil((double) totalMusicRoom / PAGE_SIZE);

        return new ChatRecordInfo(chatRecords, totalPage, page);
    }

    public void deleteChatRecord(int chatRecordId, int operatorId) {
        int musicRoomId = chatRecordMapper.getMusicRoomIdById(chatRecordId);
        // 验证是否为音乐室管理员
        List<AdminGroup> adminGroups = adminGroupMapper.getAdminGroupByMusicRoomId(musicRoomId);
        if (!adminGroups.stream().anyMatch(adminGroup -> adminGroup.getUserId() == operatorId)) {
            throw new IllegalArgumentException("Illegal insert, publicity don't allow");
        }
        chatRecordMapper.deleteChatRecord(chatRecordId);
    }

    public ChatRecord addChatRecord(AddChatRecordRequest request) {
        int musicRoomId = request.getMusicRoomId();
        int userId = request.getUserId();
        // 检查是否可以聊天
        boolean chatable = musicRoomMemberMapper.getMemberChatable(userId, musicRoomId);
        if (!chatable) {
            throw new IllegalArgumentException("Illegal chat record added");
        }

        // 合法插入聊天记录
        ChatRecord chatRecord = ChatRecord.builder()
                .content(request.getContent())
                .type(request.getType())
                .userId(request.getUserId())
                .musicRoomId(request.getMusicRoomId())
                .build();

        chatRecordMapper.insertChatRecord(chatRecord);

        return chatRecord;
    }
}
