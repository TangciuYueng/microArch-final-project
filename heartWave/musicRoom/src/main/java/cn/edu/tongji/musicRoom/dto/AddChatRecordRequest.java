package cn.edu.tongji.musicRoom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddChatRecordRequest {
    private String content;
    private String type;
    private int userId;
    private int musicRoomId;
}
