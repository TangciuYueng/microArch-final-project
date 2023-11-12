package cn.edu.tongji.musicroom.model;

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
    private MemberStatus status;

    public enum MemberStatus {
        IN,
        OUT
    }
}
