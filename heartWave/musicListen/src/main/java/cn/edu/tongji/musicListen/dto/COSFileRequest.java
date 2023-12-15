package cn.edu.tongji.musicListen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class COSFileRequest {
    private String localPath;
    private String cosPath;
}
