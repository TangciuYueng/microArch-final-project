package cn.edu.tongji.diaryWriting.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
//dto中写的内容就是假如用户调用了接口之后返回的数据类型
//假如用户新增了一篇日记，新增完之后要返回的数据如下所示
public class AddDiaryWritingRequest {
    private int id;               //该日记在该用户的列表中的id
    private int userId;          //该日记创建者的账户id
//    private Timestamp createDate;     //日记的创建日期
    private String createDate;
    private String title;         //日记的标题
    private String content;       //日记的内容
    private String coverImg;     //日记的封面图片
    private String backgroundMusic;  //日记的背景音乐
    private String permission;         //日记的权限
}
