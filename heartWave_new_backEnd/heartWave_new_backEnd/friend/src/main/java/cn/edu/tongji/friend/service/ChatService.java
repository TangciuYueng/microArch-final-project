package cn.edu.tongji.friend.service;

import cn.edu.tongji.friend.dto.AddChatRecordRequest;
import cn.edu.tongji.friend.model.RedisChatRecord;

import java.io.IOException;
import java.util.List;

public interface ChatService {
    void addChatRecord(AddChatRecordRequest addChatRecordRequest);
    List<RedisChatRecord> getChatRecord(long uid1, long uid2) throws IOException;
}
