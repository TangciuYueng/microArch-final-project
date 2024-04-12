package cn.edu.tongji.Emotions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 使用Lombok简化数据传输对象（DTO）的代码。
 * 包括自动化的构造函数、Getter、Setter、toString()方法等。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryEmotionDTO {

    private int userId;
    private int diaryId;
    private LocalDateTime createTime;
    private float happy;
    private float sad;
    private float aggressive;
    private float peace;

}
