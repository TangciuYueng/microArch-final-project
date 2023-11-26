package cn.edu.tongji.login.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRecord {
    private Integer id;
    private Integer senderId;
    private Integer receiverId;
    private String sendTime;
    private String content;
    private String type;
    private Boolean isDeleted;

//    public static void main(String[] args) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime localDateTime = LocalDateTime.parse("2023-11-25 10:08:30", formatter);
//        System.out.println(localDateTime);
//    }
}
