package cn.edu.tongji.login.service.impl;

import cn.edu.tongji.login.dto.COSFileRequest;
import cn.edu.tongji.login.service.COSService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
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
    public void uploadFile(COSFileRequest cosFileRequest) {
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为 folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        PutObjectRequest putObjectRequest = new PutObjectRequest(
                getCOSBuckets().get(0).getName(),   //目前只有一个存储桶
                cosFileRequest.getCosPath(),  //文件的key值，相当于在oss中的路径
                new File(cosFileRequest.getLocalPath())  //本地文件
        );

        cosClient.putObject(putObjectRequest);
    }

    @Override
    public void uploadFileBytes(byte[] fileBytes, String cosPath) {
        InputStream in = new ByteArrayInputStream(fileBytes);
        cosClient.putObject(getCOSBuckets().get(0).getName(), cosPath, in, null);
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
}
