package cn.edu.tongji.diaryWriting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//一些实体类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Diary {
    private int id;
    private int userId;
    private Date createDate;
    private String title;
    private String content;
    private String coverImg;
    private String backgroundMusic;
    private String permission;
}

