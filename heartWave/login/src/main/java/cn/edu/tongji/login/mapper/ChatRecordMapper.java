package cn.edu.tongji.login.mapper;

import cn.edu.tongji.login.model.ChatRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
            @Param("date") String date
    );
    List<ChatRecord> getByPage(
            @Param("senderId") int senderId,
            @Param("receiverId") int receiverId,
            @Param("date") String date,
            @Param("page") int offset
    );
    List<ChatRecord> getByTime(
            @Param("senderId") int senderId,
            @Param("receiverId") int receiverId,
            @Param("page") int offset
    );
}
