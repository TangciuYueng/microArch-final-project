package cn.edu.tongji.musicListen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class COSMusicAddRequest {
    private String localPath;
    private String cosPath;
    private String type;
    private String genre;
}
