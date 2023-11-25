package cn.edu.tongji.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddChatRecordRequest {
    private Integer senderId;
    private Integer receiverId;
    private String content;
    private String type;
}
