package cn.edu.tongji.friend.mapper;

import cn.edu.tongji.friend.model.ChatRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Mapper
@Repository
public interface ChatRecordMapper {
    ChatRecord getById(@Param("id") int id);
    int add(ChatRecord chatRecord);
    int update(ChatRecord chatRecord);
    int getCountByDate(
            @Param("senderId") int senderId,
            @Param("receiverId") int receiverId,
            @Param("date") LocalDate date
    );
    List<ChatRecord> getByPage(
            @Param("senderId") int senderId,
            @Param("receiverId") int receiverId,
            @Param("date") LocalDate date,
            @Param("offset") int offset
    );
    List<ChatRecord> getByTime(
            @Param("senderId") int senderId,
            @Param("receiverId") int receiverId,
            @Param("offset") int offset
    );
}
