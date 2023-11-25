package cn.edu.tongji.musicRoom.mapper;

import cn.edu.tongji.musicRoom.dto.AddChatRecordRequest;
import cn.edu.tongji.musicRoom.model.ChatRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatRecordMapper {
    //根据页数获得聊天记录
    List<ChatRecord> getChatRecordByPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize,@Param("musicRoomId") int musicRoomId);

    //获取所有聊天记录
    int getAllChatRecord();

    void deleteChatRecord(@Param("chatRecordId") int chatRecordId);

    //新增聊天记录
    void insertChatRecord(ChatRecord chatRecord);

    int getMusicRoomIdById(@Param("chatRecordId") int chatRecordId);
}
