package cn.edu.tongji.musicListen.controller;

import cn.edu.tongji.musicListen.dto.COSFileRequest;
import cn.edu.tongji.musicListen.dto.COSGetObjectsRequest;
import cn.edu.tongji.musicListen.dto.COSMusicAddRequest;
import cn.edu.tongji.musicListen.service.COSService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.COSObjectSummary;
import com.qcloud.cos.model.ObjectMetadata;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cos")
public class COSController {
    @Resource
    private COSService cosService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private String convertRequestToJson(COSMusicAddRequest request) {
        // 使用 Jackson 等库将请求转换为 JSON 字符串
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }

    @GetMapping("/buckets")
    public ResponseEntity<?> getCOSBuckets() {
        try {
            List<Bucket> buckets = cosService.getCOSBuckets();
            return new ResponseEntity<>(buckets, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get COS buckets failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> uploadFile(@RequestBody COSMusicAddRequest cosMusicAddRequest) {
        try {
            cosService.uploadFile(cosMusicAddRequest);

            // 将请求体信息转换为字符串（JSON格式）
            String message = convertRequestToJson(cosMusicAddRequest);

            // 发送消息到 RabbitMQ
            rabbitTemplate.convertAndSend("music", "newMusic", message);


            return new ResponseEntity<>("successfully upload file to cos", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("upload file to cos failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 叫getObjects的原因是：oss中文件夹和文件都是Object
     */
    @GetMapping("/get")
    public ResponseEntity<?> getObjects(@RequestBody COSGetObjectsRequest cosGetObjectsRequest) {
        try {
            List<COSObjectSummary> cosObjectSummaries = cosService.getObjects(cosGetObjectsRequest);
            return new ResponseEntity<>(cosObjectSummaries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get files from cos failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadFile(@RequestBody COSFileRequest cosFileRequest) {
        try {
            ObjectMetadata objectMetadata = cosService.downloadFile(cosFileRequest);
            return new ResponseEntity<>(objectMetadata, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("download file from cos failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 只能删除文件（oss在文件夹为空后，会自动删除文件夹）
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFile(@RequestBody COSFileRequest cosFileRequest) {
        try {
            cosService.deleteFile(cosFileRequest);
            return new ResponseEntity<>("successfully delete file from cos", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("delete file from cos failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
