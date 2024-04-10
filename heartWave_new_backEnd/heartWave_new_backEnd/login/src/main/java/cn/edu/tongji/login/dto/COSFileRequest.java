package cn.edu.tongji.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class COSFileRequest {
    private String localPath;
    private String cosPath;
}
