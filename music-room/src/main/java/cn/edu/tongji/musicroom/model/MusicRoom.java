package cn.edu.tongji.musicroom.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicRoom {
    private int id;
    private String title;
    private String theme;
    private String description;
    private String status;
}
