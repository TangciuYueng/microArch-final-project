package cn.edu.tongji.friend.dto;

import cn.edu.tongji.friend.model.ChatRecordType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddChatRecordRequest {
    private Long senderId;
    private Long receiverId;
    private ChatRecordType type;
    private String content;
}
