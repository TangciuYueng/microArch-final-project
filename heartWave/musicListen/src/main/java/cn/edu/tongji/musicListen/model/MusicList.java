package cn.edu.tongji.musicListen.model;

import java.time.LocalDate;

public class MusicList {
    private int id;
    private String type;
    private int user_id;
    private int music_id;
    private LocalDate create_date;
    private int list_order;
    private String description;
    private int play_count;
    private boolean is_deleted;
}
