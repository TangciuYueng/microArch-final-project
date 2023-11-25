package cn.edu.tongji.login.service;

import cn.edu.tongji.login.dto.AddChatRecordRequest;
import cn.edu.tongji.login.dto.GetChatRecordPageByTimeRequest;
import cn.edu.tongji.login.dto.GetChatRecordPageCountByDateRequest;
import cn.edu.tongji.login.dto.GetChatRecordPageByDateRequest;
import cn.edu.tongji.login.mapper.ChatRecordMapper;
import cn.edu.tongji.login.model.ChatRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRecordService {
    private final ChatRecordMapper chatRecordMapper;

    public int addChatRecord(AddChatRecordRequest addChatRecordRequest) {
        return chatRecordMapper.addChatRecord(addChatRecordRequest);
    }

    public int deleteChatRecord(int id) {
        return chatRecordMapper.deleteChatRecord(id);
    }

    public int getChatRecordPageCountByDate(GetChatRecordPageCountByDateRequest getChatRecordPageCountByDateRequest) {
        return chatRecordMapper.getChatRecordPageCountByDate(getChatRecordPageCountByDateRequest);
    }

    public List<ChatRecord> getChatRecordByPage(GetChatRecordPageByDateRequest getChatRecordPageByDateRequest) {
        getChatRecordPageByDateRequest.setPage(getChatRecordPageByDateRequest.getPage() * 2);
        return chatRecordMapper.getChatRecordByPage(getChatRecordPageByDateRequest);
    }

    public List<ChatRecord> getChatRecordByTime(GetChatRecordPageByTimeRequest getChatRecordPageByTimeRequest) {
        getChatRecordPageByTimeRequest.setPage(getChatRecordPageByTimeRequest.getPage() * 2);
        return chatRecordMapper.getChatRecordByTime(getChatRecordPageByTimeRequest);
    }
}
