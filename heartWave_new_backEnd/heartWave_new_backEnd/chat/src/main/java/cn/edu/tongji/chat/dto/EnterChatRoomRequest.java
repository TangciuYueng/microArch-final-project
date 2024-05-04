package cn.edu.tongji.chat.dto;

import cn.edu.tongji.chat.model.ChatPortType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnterChatRoomRequest {
    private Long selfId;
    private Long remoteId;
    private ChatPortType type;
    private Long groupId;
}
