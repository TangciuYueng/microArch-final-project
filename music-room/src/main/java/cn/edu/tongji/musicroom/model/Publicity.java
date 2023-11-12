package cn.edu.tongji.musicroom.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Publicity {
    private int id;
    private Type type;
    private int musicRoomId;
    private int userId;
    private LocalDateTime createAt;

    public enum Type {
        PUBLIC,
        LIMITED
    }
}
