package cn.edu.tongji.friend.controller;

import cn.edu.tongji.friend.dto.AddChatRecordRequest;
import cn.edu.tongji.friend.dto.UpdateChatTimeRequest;
import cn.edu.tongji.friend.service.ChatService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
