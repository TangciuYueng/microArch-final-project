package cn.edu.tongji.musicRoom.dto;

import cn.edu.tongji.musicRoom.model.MusicRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicRoomInfo {
    private List<MusicRoom> musicRooms;
    private int totalPage;
    private int currentPage;
}
