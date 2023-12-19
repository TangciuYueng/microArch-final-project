package cn.edu.tongji.friend.service;

import cn.edu.tongji.friend.dto.AddChatRecordRequest;
import cn.edu.tongji.friend.dto.GetChatRecordPageByTimeRequest;
import cn.edu.tongji.friend.dto.GetChatRecordPageCountByDateRequest;
import cn.edu.tongji.friend.dto.GetChatRecordPageByDateRequest;
import cn.edu.tongji.friend.model.ChatRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChatRecordService {
    ChatRecord addChatRecord(AddChatRecordRequest addChatRecordRequest);
    void deleteChatRecord(int id);
    int getChatRecordPageCountByDate(GetChatRecordPageCountByDateRequest getChatRecordPageCountByDateRequest);
    List<ChatRecord> getChatRecordByPage(GetChatRecordPageByDateRequest getChatRecordPageByDateRequest);
    List<ChatRecord> getChatRecordByTime(GetChatRecordPageByTimeRequest getChatRecordPageByTimeRequest);
}
