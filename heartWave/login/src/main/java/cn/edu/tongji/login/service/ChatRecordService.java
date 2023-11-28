package cn.edu.tongji.login.service;

import cn.edu.tongji.login.dto.AddChatRecordRequest;
import cn.edu.tongji.login.dto.GetChatRecordPageByTimeRequest;
import cn.edu.tongji.login.dto.GetChatRecordPageCountByDateRequest;
import cn.edu.tongji.login.dto.GetChatRecordPageByDateRequest;
import cn.edu.tongji.login.model.ChatRecord;
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
