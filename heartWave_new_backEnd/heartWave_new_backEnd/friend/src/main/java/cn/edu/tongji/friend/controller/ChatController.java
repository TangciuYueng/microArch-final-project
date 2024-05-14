package cn.edu.tongji.friend.controller;

import cn.edu.tongji.friend.dto.AddChatRecordRequest;
import cn.edu.tongji.friend.dto.UpdateChatTimeRequest;
import cn.edu.tongji.friend.model.RedisChatRecord;
import cn.edu.tongji.friend.service.ChatService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @Resource
    private ChatService chatService;

    @PostMapping
    public ResponseEntity<?> addChatRecord(@RequestBody AddChatRecordRequest addChatRecordRequest) {
        try {
            chatService.addChatRecord(addChatRecordRequest);
            return new ResponseEntity<>("successfully added chat record", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("add chat record failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/time")
    public ResponseEntity<?> updateChatTime(@RequestBody UpdateChatTimeRequest updateChatTimeRequest) {
        try {
            chatService.updateChatTime(updateChatTimeRequest);
            return new ResponseEntity<>("successfully added chat time", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("add chat time failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getChatRecord(
            @RequestParam("uid1") long uid1,
            @RequestParam("uid2") long uid2) {
        try {
            List<RedisChatRecord> chatRecords = chatService.getChatRecord(uid1, uid2);
            return new ResponseEntity<>(chatRecords, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get chat record failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
