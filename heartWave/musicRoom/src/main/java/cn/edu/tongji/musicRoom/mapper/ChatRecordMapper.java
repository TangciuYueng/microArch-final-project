package cn.edu.tongji.musicRoom.mapper;

import cn.edu.tongji.musicRoom.dto.AddChatRecordRequest;
import cn.edu.tongji.musicRoom.model.ChatRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatRecordMapper {

    List<ChatRecord> getChatRecordByPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize,@Param("musicRoomId") int musicRoomId);

    int getAllChatRecord();

    void deleteChatRecord(@Param("chatRecordId") int chatRecordId, @Param("isDeleted") int isDeleted);

    void insertChatRecord(AddChatRecordRequest addChatRecordRequest);
}
