package cn.edu.tongji.room.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CloseRequest {
    private int port;
    private int operatorId;
    private int musicRoomId;
}
