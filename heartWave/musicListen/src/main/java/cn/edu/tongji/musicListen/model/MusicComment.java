package cn.edu.tongji.musicListen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicComment {
    private int id;
    private int senderId;
    private int musicId;
    private String context;
    private LocalDate sendTime;
    private int likes;
}
