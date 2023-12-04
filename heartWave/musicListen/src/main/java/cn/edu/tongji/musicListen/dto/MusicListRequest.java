package cn.edu.tongji.musicListen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicListRequest {
    private Integer musicListId;
    private String type;
    private int userId;
    private int musicId;
    private String description;
}
