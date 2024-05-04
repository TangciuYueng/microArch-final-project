package cn.edu.tongji.chat.controller;

import cn.edu.tongji.chat.dto.ChatResult;
import cn.edu.tongji.chat.dto.EnterChatRoomRequest;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cn.edu.tongji.chat.service.ChatPortService;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @Resource
    private ChatPortService chatPortService;

    @PutMapping
    public ResponseEntity<?> enterChatRoom(@RequestBody EnterChatRoomRequest enterChatRoomRequest) {
        try {
            ChatResult chatResult = chatPortService.enterChatRoom(enterChatRoomRequest);
            return new ResponseEntity<>(chatResult, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get new chat room failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> defaultPort() {
        try {
            chatPortService.defaultPort();
            return new ResponseEntity<>("successfully default port", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("default port failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
