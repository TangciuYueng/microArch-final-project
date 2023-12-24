package cn.edu.tongji.musicListen.service;


import cn.edu.tongji.musicListen.dto.COSFileRequest;
import cn.edu.tongji.musicListen.dto.COSGetObjectsRequest;
import cn.edu.tongji.musicListen.dto.COSMusicAddRequest;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.COSObjectSummary;
import com.qcloud.cos.model.ObjectMetadata;

import java.util.List;

public interface COSService {
    List<Bucket> getCOSBuckets();
    int uploadFile(COSMusicAddRequest cosMusicAddRequest);
    List<COSObjectSummary> getObjects(COSGetObjectsRequest cosGetObjectsRequest);
    ObjectMetadata downloadFile(COSFileRequest cosFileRequest);
    void deleteFile(COSFileRequest cosFileRequest);
}
