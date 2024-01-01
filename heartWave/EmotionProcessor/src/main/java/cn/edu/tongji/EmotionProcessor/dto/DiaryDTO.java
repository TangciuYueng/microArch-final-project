package cn.edu.tongji.EmotionProcessor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryDTO {

    private int id;               //该日记在该用户的列表中的id
    private int userId;          //该日记创建者的账户id
    private String createDate;
    private String updateTime;
    private int currentStatus;
    private String title;         //日记的标题
    private String content;       //日记的内容
    private String coverImg;     //日记的封面图片
    private String backgroundMusic;  //日记的背景音乐
    private String permission;         //日记的权限
}
