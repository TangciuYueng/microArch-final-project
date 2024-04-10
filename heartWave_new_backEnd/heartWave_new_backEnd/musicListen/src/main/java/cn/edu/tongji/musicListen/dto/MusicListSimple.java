package cn.edu.tongji.musicListen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MusicListSimple {
    Integer id;
    String name;
    String src;
}
