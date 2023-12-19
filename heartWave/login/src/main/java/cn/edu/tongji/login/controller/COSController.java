package cn.edu.tongji.login.controller;

import cn.edu.tongji.login.dto.COSGetObjectsRequest;
import cn.edu.tongji.login.dto.COSFileRequest;
import cn.edu.tongji.login.service.COSService;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.COSObjectSummary;
import com.qcloud.cos.model.ObjectMetadata;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cos")
public class COSController {
    @Resource
    private COSService cosService;

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

    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestBody COSFileRequest cosFileRequest) {
        try {
            cosService.uploadFile(cosFileRequest);
            return new ResponseEntity<>("successfully upload file to cos", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("upload file to cos failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 叫getObjects的原因是：oss中文件夹和文件都是Object
     */
    @GetMapping
    public ResponseEntity<?> getObjects(@RequestBody COSGetObjectsRequest cosGetObjectsRequest) {
        try {
            List<COSObjectSummary> cosObjectSummaries = cosService.getObjects(cosGetObjectsRequest);
            return new ResponseEntity<>(cosObjectSummaries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get files from cos failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/file")
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
    @DeleteMapping
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
