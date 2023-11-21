package cn.edu.tongji.musicRoom.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicRoomMember {
    private int id;
    private int musicRoomId;
    private int userId;
    private boolean singable;
    private boolean chatable;
    private String status;
}
