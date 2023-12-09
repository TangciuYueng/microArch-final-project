package cn.edu.tongji.room.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MusicRoom {
    private int id;
    private int creatorId;
    private LocalDateTime createTime;
    private String title;
    private String theme;
    private String description;
    private String status;
}
