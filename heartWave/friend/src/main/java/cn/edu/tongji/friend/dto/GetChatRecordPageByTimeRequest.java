package cn.edu.tongji.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetChatRecordPageByTimeRequest {
    private Integer senderId;
    private Integer receiverId;
    private Integer page;
}
