package cn.edu.tongji.musicRoom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSingableRequest {
    private int userId;
    private int musicRoomId;
    private boolean singable;
}
