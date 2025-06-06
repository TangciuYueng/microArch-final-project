package cn.edu.tongji.musicRoom.service.impl;

import cn.edu.tongji.musicRoom.dto.AddChatRecordRequest;
import cn.edu.tongji.musicRoom.dto.ChatRecordInfo;
import cn.edu.tongji.musicRoom.mapper.AdminGroupMapper;
import cn.edu.tongji.musicRoom.mapper.ChatRecordMapper;
import cn.edu.tongji.musicRoom.mapper.MusicRoomMemberMapper;
import cn.edu.tongji.musicRoom.model.AdminGroup;
import cn.edu.tongji.musicRoom.model.ChatRecord;
import cn.edu.tongji.musicRoom.service.ChatRecordService;
import cn.edu.tongji.musicRoom.util.ObjectToOtherUtil;
import cn.edu.tongji.musicRoom.util.RedisOperatingUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRecordServiceImpl implements ChatRecordService {
    @Resource
    private ChatRecordMapper chatRecordMapper;
    @Resource
    private MusicRoomMemberMapper musicRoomMemberMapper;
    @Resource
    private AdminGroupMapper adminGroupMapper;
//    @Resource
//    private ChatRecordRepository chatRecordRepository;
    @Resource
    private RedisOperatingUtil redisOperatingUtil;

    private final static int PAGE_SIZE = 30;

    // 通过调用ChatRecordMapper的方法获取指定音乐房间ID下的聊天记录列表，
    // 并通过ChatRecordInfo对象封装返回结果，包括分页后的聊天记录列表，总页数和当前页码。
    @Override
    public ChatRecordInfo getAllChatRecord(int page, Integer musicRoomId) {
        int startIndex = (page - 1) * PAGE_SIZE;
        List<ChatRecord> chatRecords = chatRecordMapper.getChatRecordByPage(startIndex, PAGE_SIZE, musicRoomId);
        // 获取所有聊天记录
        int totalMusicRoom = chatRecordMapper.getAllChatRecord(musicRoomId);
        int totalPage = (int) Math.ceil((double) totalMusicRoom / PAGE_SIZE);

        // 构建返回类
        ChatRecordInfo chatRecordInfo = ChatRecordInfo.builder()
                .chatRecords(chatRecords)
                .currentPage(page)
                .totalPage(totalPage)
                .build();

        return chatRecordInfo;
    }

    @Override
    public void deleteChatRecord(int chatRecordId, int operatorId) {
        int musicRoomId = chatRecordMapper.getMusicRoomIdById(chatRecordId);
        // 验证是否为音乐室管理员
        List<AdminGroup> adminGroups = adminGroupMapper.getAdminGroupByMusicRoomId(musicRoomId);
        if (!adminGroups.stream().anyMatch(adminGroup -> adminGroup.getUserId() == operatorId)) {
            throw new IllegalArgumentException("Illegal insert, publicity don't allow");
        }
        chatRecordMapper.deleteChatRecord(chatRecordId);
    }

    @Override
    public ChatRecord addChatRecord(AddChatRecordRequest request) {
        int musicRoomId = request.getMusicRoomId();
        int userId = request.getUserId();
        // 检查是否可以聊天
        Boolean chatable = musicRoomMemberMapper.getMemberChatable(userId, musicRoomId);
        if (chatable == null || !chatable) {
            throw new IllegalArgumentException("Illegal chat record added");
        }

        // 合法插入聊天记录
        ChatRecord chatRecord = ChatRecord.builder()
                .content(request.getContent())
                .type(request.getType())
                .userId(request.getUserId())
                .musicRoomId(request.getMusicRoomId())
                .build();

        // 插入 Redis 插入聊天记录
        redisOperatingUtil.listRightPush("chatRecord", chatRecord);

        // 插入数据库
//        chatRecordMapper.insertChatRecord(chatRecord);

        return chatRecord;
    }
}
