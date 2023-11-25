package cn.edu.tongji.musicRoom.dto;

import cn.edu.tongji.musicRoom.model.AdminGroup;
import cn.edu.tongji.musicRoom.model.MusicRoom;
import cn.edu.tongji.musicRoom.model.MusicRoomMember;
import cn.edu.tongji.musicRoom.model.Publicity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MusicRoomDetailed {
    private MusicRoom musicRoom;
    private List<MusicRoomMember> musicRoomMembers;
    private List<AdminGroup> adminGroups;
    private PublicityDTO publicity;
}
