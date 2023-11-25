package cn.edu.tongji.login.mapper;

import cn.edu.tongji.login.dto.AddChatRecordRequest;
import cn.edu.tongji.login.dto.GetChatRecordPageByTimeRequest;
import cn.edu.tongji.login.dto.GetChatRecordPageCountByDateRequest;
import cn.edu.tongji.login.dto.GetChatRecordPageByDateRequest;
import cn.edu.tongji.login.model.ChatRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ChatRecordMapper {
    int addChatRecord(AddChatRecordRequest addChatRecordRequest);
    int deleteChatRecord(int id);
    int getChatRecordPageCountByDate(GetChatRecordPageCountByDateRequest getChatRecordPageCountByDateRequest);
    List<ChatRecord> getChatRecordByPage(GetChatRecordPageByDateRequest getChatRecordPageByDateRequest);
    List<ChatRecord> getChatRecordByTime(GetChatRecordPageByTimeRequest getChatRecordPageByTimeRequest);
}
