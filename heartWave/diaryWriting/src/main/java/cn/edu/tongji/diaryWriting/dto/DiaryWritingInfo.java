package cn.edu.tongji.diaryWriting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//// 处理前后端交互的request or response类
public class DiaryWritingInfo {
    private String updateTime;
    private int currentStatus;
    private String title;         //日记的标题
    private String content;       //日记的内容
    private String coverImg;     //日记的封面图片
    private String backgroundMusic;  //日记的背景音乐
    private String permission;         //日记的权限
}
