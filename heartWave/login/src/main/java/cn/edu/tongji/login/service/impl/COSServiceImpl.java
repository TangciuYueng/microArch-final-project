package cn.edu.tongji.login.service.impl;

import cn.edu.tongji.login.config.TengxunyunCOSConfig;
import cn.edu.tongji.login.dto.COSGetObjectsRequest;
import cn.edu.tongji.login.dto.COSFileRequest;
import cn.edu.tongji.login.service.COSService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.*;
import jakarta.annotation.Resource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableConfigurationProperties(TengxunyunCOSConfig.class)
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
    public List<COSObjectSummary> getObjects(COSGetObjectsRequest cosGetObjectsRequest) {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        listObjectsRequest.setBucketName(getCOSBuckets().get(0).getName());  //存储桶名称（目前只有一个存储桶）
        //文件的key前缀，类似目录
        //设置时要注意和下面的隔断符对应上
        //隔断符为/时，prefix请以/结尾，如：music/happy/，否则会返回空
        //隔断符为空时，无脑返回所有key以设置的prefix为前缀的对象
        listObjectsRequest.setPrefix(cosGetObjectsRequest.getPrefix());
        //隔断符
        //设为/则返回当前目录以及当前目录下的文件（不返回子目录及其内容）
        //设为空串则返回当前目录及其所有内容
        //其余设置非法，请求时会报错
        listObjectsRequest.setDelimiter(cosGetObjectsRequest.getDelimiter());
        listObjectsRequest.setMaxKeys(1000);   //设置最大遍历出多少个对象，最大支持1000
        ObjectListing objectListing;
        List<COSObjectSummary> ret = new ArrayList<>();

        do {
            objectListing = cosClient.listObjects(listObjectsRequest);

            //当delimiter设置为/时，common prefix表示所有子目录的路径（以/结尾）
            //当delimiter为空时，没有用，List长度为0
            List<String> commonPrefixs = objectListing.getCommonPrefixes();
            for (String commonPrefix: commonPrefixs) {
                System.out.println(commonPrefix);
            }

            //将本次遍历得到的对象追加到返回数组
            ret.addAll(objectListing.getObjectSummaries());

            listObjectsRequest.setMarker(objectListing.getNextMarker());
        } while (objectListing.isTruncated());

        return ret;
    }

    @Override
    public ObjectMetadata downloadFile(COSFileRequest cosFileRequest) {
        File downloadFile = new File(cosFileRequest.getLocalPath());  //下载到的本地文件
        GetObjectRequest getObjectRequest = new GetObjectRequest(
                getCOSBuckets().get(0).getName(),  //存储桶名称（目前只有一个存储桶）
                cosFileRequest.getCosPath()        //文件key
        );

        return cosClient.getObject(getObjectRequest, downloadFile);
    }

    @Override
    public void deleteFile(COSFileRequest cosFileRequest) {
        cosClient.deleteObject(
                getCOSBuckets().get(0).getName(),
                cosFileRequest.getCosPath()
        );
    }
}
