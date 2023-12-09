package cn.edu.tongji.room.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicRoomDTO {
    private String title;
    private String theme;
    private String description;
    private String publicity;
    private List<Integer> userIds;
    private int creatorId;
}
