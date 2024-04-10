package cn.edu.tongji.login.service;

import cn.edu.tongji.login.dto.COSFileRequest;
import com.qcloud.cos.model.Bucket;

import java.util.List;

public interface COSService {
    List<Bucket> getCOSBuckets();
    void uploadFile(COSFileRequest cosFileRequest);
}
