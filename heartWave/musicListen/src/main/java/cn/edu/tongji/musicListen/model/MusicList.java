package cn.edu.tongji.musicListen.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MusicList {
    private int id;
    private String type;
    private int userId;
    private int musicId;
    private LocalDate createDate;
    private int listOrder;
    private String description;
    private int playCount;
    private boolean isDeleted;
}
