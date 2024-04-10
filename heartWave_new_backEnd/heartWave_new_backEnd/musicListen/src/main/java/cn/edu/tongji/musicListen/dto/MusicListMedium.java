package cn.edu.tongji.musicListen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MusicListMedium {
    private Integer id;
    private String name;
    private String type;
    private LocalDate createDate;
    private String src;
    private Integer playCount;
}
