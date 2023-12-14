package cn.edu.tongji.musicListen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class COSGetObjectsRequest {
    private String prefix;
    private String delimiter;
}
