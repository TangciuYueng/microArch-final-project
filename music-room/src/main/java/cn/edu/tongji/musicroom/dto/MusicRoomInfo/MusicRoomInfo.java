package cn.edu.tongji.musicroom.dto.MusicRoomInfo;

import cn.edu.tongji.musicroom.model.MusicRoom;
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
}
// 处理前后端交互的request or response类