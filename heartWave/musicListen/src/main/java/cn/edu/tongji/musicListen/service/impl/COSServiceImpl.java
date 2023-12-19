package cn.edu.tongji.musicListen.service.impl;


import cn.edu.tongji.musicListen.config.TengxunyunCOSConfig;
import cn.edu.tongji.musicListen.dto.COSFileRequest;
import cn.edu.tongji.musicListen.dto.COSGetObjectsRequest;
import cn.edu.tongji.musicListen.dto.COSMusicAddRequest;
import cn.edu.tongji.musicListen.mapper.MusicMapper;
import cn.edu.tongji.musicListen.model.Music;
import cn.edu.tongji.musicListen.service.COSService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.*;
import jakarta.annotation.Resource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableConfigurationProperties(TengxunyunCOSConfig.class)
public class COSServiceImpl implements COSService {
    @Resource
    private COSClient cosClient;

    @Resource
    private MusicMapper musicMapper;

    @Override
    public List<Bucket> getCOSBuckets() {
        return cosClient.listBuckets();
    }

    @Transactional
    @Override
    public void uploadFile(COSMusicAddRequest cosMusicAddRequest) {
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为 folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        PutObjectRequest putObjectRequest = new PutObjectRequest(
                getCOSBuckets().get(0).getName(),  //目前只有一个存储桶
                cosMusicAddRequest.getCosPath(),  //文件的key值，相当于在oss中的路径
                new File(cosMusicAddRequest.getLocalPath())  //本地文件
        );

        cosClient.putObject(putObjectRequest);
        // 增加对数据库的增加操作

        // titie 通过 localPath提取
        // 创建 File 对象
        File file = new File(cosMusicAddRequest.getLocalPath());

        // 获取文件名
        String fileName = file.getName();
        // Music中其他属性需要添加者自行设置，目前情绪推荐版块只关心titile,src.type,genre,就只设置了这些属性到music表中
        // 构建 Music
        Music music = Music.builder()
                .title(fileName)
                .type(cosMusicAddRequest.getType())
                .src(cosMusicAddRequest.getCosPath())
                .genre(cosMusicAddRequest.getGenre())
                .album(cosMusicAddRequest.getAlbum())
                .label(cosMusicAddRequest.getLabel())
                .likes(cosMusicAddRequest.getLikes())
                .artist(cosMusicAddRequest.getArtist())
                .composer(cosMusicAddRequest.getComposer())
                .downloads(cosMusicAddRequest.getDownloads())
                .duration(cosMusicAddRequest.getDuration())
                .musicMood(cosMusicAddRequest.getMusicMood())
                .lyrics(cosMusicAddRequest.getLyrics())
                .language(cosMusicAddRequest.getLanguage())
                .producer(cosMusicAddRequest.getProducer())
                .releaseDate(cosMusicAddRequest.getReleaseDate())
                .writer(cosMusicAddRequest.getWriter())
                .playCount(cosMusicAddRequest.getPlayCount())
                .build();
        // 插入 music 表
        int newId = musicMapper.insertMusic(music);

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
