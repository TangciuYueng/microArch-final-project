package cn.edu.tongji.diaryWriting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiarySimple {
    private Integer id;
    private LocalDate createDate;
    private LocalDate updateTime;
    private Integer currentStatus;
    private String title;
    private String cover;
    private String permission;
}
