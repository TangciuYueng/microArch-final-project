package cn.edu.tongji.musicRoom.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminGroup {
    private int id;
    private int musicRoomId;
    private int userId;
}
