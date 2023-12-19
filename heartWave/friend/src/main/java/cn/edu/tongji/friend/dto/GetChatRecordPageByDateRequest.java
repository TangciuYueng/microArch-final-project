package cn.edu.tongji.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetChatRecordPageByDateRequest {
    private Integer senderId;
    private Integer receiverId;
    private LocalDate date;
    private Integer page;
}
