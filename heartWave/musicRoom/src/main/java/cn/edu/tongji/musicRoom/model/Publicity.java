package cn.edu.tongji.musicRoom.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Publicity {
    private int id;
    private String type;
    private int musicRoomId;
    private int userId;
    private LocalDateTime createAt;
}
