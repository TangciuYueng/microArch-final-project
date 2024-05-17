package cn.edu.tongji.friend.service;

import com.qcloud.cos.model.Bucket;

import java.io.IOException;
import java.util.List;

public interface COSService {
    List<Bucket> getCOSBuckets();
    String getUserAvatar(Long userId) throws IOException;
    void uploadFileBytes(byte[] fileBytes, String cosPath);
    String getChatImage(Long recordId) throws IOException;
}
