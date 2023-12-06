package cn.edu.tongji.login.service;

import cn.edu.tongji.login.dto.COSGetObjectsRequest;
import cn.edu.tongji.login.dto.COSFileRequest;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.COSObjectSummary;
import com.qcloud.cos.model.ObjectMetadata;

import java.util.List;

public interface COSService {
    List<Bucket> getCOSBuckets();
    void uploadFile(COSFileRequest cosFileRequest);
    List<COSObjectSummary> getObjects(COSGetObjectsRequest cosGetObjectsRequest);
    ObjectMetadata downloadFile(COSFileRequest cosFileRequest);
    void deleteFile(COSFileRequest cosFileRequest);
}
