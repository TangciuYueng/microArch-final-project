package cn.edu.tongji.login.controller;

import cn.edu.tongji.login.dto.AddChatRecordRequest;
import cn.edu.tongji.login.dto.GetChatRecordPageByTimeRequest;
import cn.edu.tongji.login.dto.GetChatRecordPageCountByDateRequest;
import cn.edu.tongji.login.dto.GetChatRecordPageByDateRequest;
import cn.edu.tongji.login.model.ChatRecord;
import cn.edu.tongji.login.service.ChatRecordService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat-record")
public class ChatRecordController {
    @Resource
    private ChatRecordService chatRecordService;

    @PostMapping
    public ResponseEntity<?> addChatRecord(@RequestBody AddChatRecordRequest addChatRecordRequest) {
        try {
            ChatRecord chatRecord = chatRecordService.addChatRecord(addChatRecordRequest);
            return new ResponseEntity<>(chatRecord, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("add chat record failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChatRecord(@PathVariable("id") int id) {
        try {
            chatRecordService.deleteChatRecord(id);
            return new ResponseEntity<>("successfully delete chat record", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("delete chat record failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> getChatRecordPageCountByDate(@RequestBody GetChatRecordPageCountByDateRequest getChatRecordPageCountByDateRequest) {
        try {
            int chatRecordPageCount = chatRecordService.getChatRecordPageCountByDate(getChatRecordPageCountByDateRequest);
            return new ResponseEntity<>(chatRecordPageCount, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get chat record page count by date failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pages")
    public ResponseEntity<?> getChatRecordByPage(@RequestBody GetChatRecordPageByDateRequest getChatRecordPageByDateRequest) {
        try {
            List<ChatRecord> chatRecords = chatRecordService.getChatRecordByPage(getChatRecordPageByDateRequest);
            return new ResponseEntity<>(chatRecords, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get chat record by page failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/time")
    public ResponseEntity<?> getChatRecordByTime(@RequestBody GetChatRecordPageByTimeRequest getChatRecordPageByTimeRequest) {
        try {
            List<ChatRecord> chatRecords = chatRecordService.getChatRecordByTime(getChatRecordPageByTimeRequest);
            return new ResponseEntity<>(chatRecords, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get chat record by time failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
