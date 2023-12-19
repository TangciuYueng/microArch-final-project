package cn.edu.tongji.musicRoom.service;

import cn.edu.tongji.musicRoom.dto.AddChatRecordRequest;
import cn.edu.tongji.musicRoom.dto.ChatRecordInfo;
import cn.edu.tongji.musicRoom.model.ChatRecord;
import org.springframework.stereotype.Service;


@Service
// 用来实现聊天的业务逻辑
public interface ChatRecordService {
    ChatRecordInfo getAllChatRecord(int page, Integer musicRoomId);

    void deleteChatRecord(int chatRecordId, int operatorId);

    ChatRecord addChatRecord(AddChatRecordRequest request);

}