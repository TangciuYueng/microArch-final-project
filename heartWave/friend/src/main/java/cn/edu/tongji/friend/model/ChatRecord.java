package cn.edu.tongji.friend.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChatRecord {
    private Integer id;
    private Integer senderId;
    private Integer receiverId;
    private LocalDateTime sendTime;
    private String content;
    private String type;
    private Boolean isDeleted;

//    public static void main(String[] args) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime localDateTime = LocalDateTime.parse("2023-11-25 10:08:30", formatter);
//        System.out.println(localDateTime);
//    }
}
