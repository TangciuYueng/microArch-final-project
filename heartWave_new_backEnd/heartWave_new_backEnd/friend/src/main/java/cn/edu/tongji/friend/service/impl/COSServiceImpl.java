package cn.edu.tongji.friend.service.impl;

import cn.edu.tongji.friend.service.COSService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

@Service
public class COSServiceImpl implements COSService {
    @Resource
    private COSClient cosClient;

    @Override
    public List<Bucket> getCOSBuckets() {
        return cosClient.listBuckets();
    }

    @Override
    public String getUserAvatar(Long userId) throws IOException {
        GetObjectRequest getObjectRequest = new GetObjectRequest(getCOSBuckets().get(0).getName(), "userAvatar/" + userId + ".jpg");
        COSObject cosObject = cosClient.getObject(getObjectRequest);
        COSObjectInputStream cosObjectInputStream = cosObject.getObjectContent();
        String base64 = Base64.getEncoder().encodeToString(cosObjectInputStream.readAllBytes());
        cosObjectInputStream.close();
        return base64;
    }

    @Override
    public void uploadFileBytes(byte[] fileBytes, String cosPath) {
        InputStream in = new ByteArrayInputStream(fileBytes);
        cosClient.putObject(getCOSBuckets().get(0).getName(), cosPath, in, null);
    }

    @Override
    public String getChatImage(Long recordId) throws IOException {
        GetObjectRequest getObjectRequest = new GetObjectRequest(getCOSBuckets().get(0).getName(), "chatRecord/single/" + recordId + ".jpg");
        COSObject cosObject = cosClient.getObject(getObjectRequest);
        COSObjectInputStream cosObjectInputStream = cosObject.getObjectContent();
        String base64 = Base64.getEncoder().encodeToString(cosObjectInputStream.readAllBytes());
        cosObjectInputStream.close();
        return base64;
    }
}
