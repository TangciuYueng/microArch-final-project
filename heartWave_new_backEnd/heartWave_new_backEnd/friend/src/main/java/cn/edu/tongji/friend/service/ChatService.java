package cn.edu.tongji.friend.service;

import cn.edu.tongji.friend.dto.AddChatRecordRequest;
import cn.edu.tongji.friend.dto.UpdateChatTimeRequest;

public interface ChatService {
    void addChatRecord(AddChatRecordRequest addChatRecordRequest);
    void updateChatTime(UpdateChatTimeRequest updateChatTimeRequest);
}
