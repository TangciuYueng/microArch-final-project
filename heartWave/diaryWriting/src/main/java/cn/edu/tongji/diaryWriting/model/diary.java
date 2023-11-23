package cn.edu.tongji.diaryWriting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//一些实体类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class diary {
    private int id;
    private int user_id;
    private Date create_date;
    private String title;
    private String content;
    private String cover_img;
    private String background_music;
    private int permission;
}

