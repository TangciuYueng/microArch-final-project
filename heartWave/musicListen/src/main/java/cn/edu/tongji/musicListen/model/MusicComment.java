package cn.edu.tongji.musicListen.model;

import java.time.LocalDate;
import java.util.Date;

public class MusicComment {
    private int id;
    private int sender_id;
    private int music_id;
    private String context;
    private LocalDate send_time;
    private int likes;
}
