package cn.edu.tongji.musicListen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultiMusicMusicListRequest {
    private String type;
    private int userId;
    private List<Integer> musicIds;
    private String description;
}
