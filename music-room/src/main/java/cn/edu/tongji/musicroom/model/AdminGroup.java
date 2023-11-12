package cn.edu.tongji.musicroom.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminGroup {
    private int id;
    private int musicRoomId;
    private int userId;
}
