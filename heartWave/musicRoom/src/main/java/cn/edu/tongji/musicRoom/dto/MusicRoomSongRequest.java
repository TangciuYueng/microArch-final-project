package cn.edu.tongji.musicRoom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicRoomSongRequest {
    private int userId;
    private String type;
    private String src;
    private String language;
}

