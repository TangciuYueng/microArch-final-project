package cn.edu.tongji.musicRoom.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicRoom {
    private int id;
    private int creatorId;
    private Date createTime;
    private String title;
    private String theme;
    private String description;
    private String status;
}
