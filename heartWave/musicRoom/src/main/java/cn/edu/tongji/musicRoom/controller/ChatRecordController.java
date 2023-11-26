package cn.edu.tongji.musicRoom.controller;

import cn.edu.tongji.musicRoom.dto.AddChatRecordRequest;
import cn.edu.tongji.musicRoom.dto.ChatRecordInfo;
import cn.edu.tongji.musicRoom.model.ChatRecord;
import cn.edu.tongji.musicRoom.service.ChatRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat_record")
@RequiredArgsConstructor
public class ChatRecordController {
    private final ChatRecordService chatRecordService;

    @PostMapping
    public ResponseEntity<?> addChatRecord(@RequestBody AddChatRecordRequest request) {
        try {
            ChatRecord chatRecord = chatRecordService.addChatRecord(request);
            return new ResponseEntity<>(chatRecord, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            String errMsg = "chat record added failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{chatRecordId}/{operatorId}")
    public ResponseEntity<?> deleteChatRecord(@PathVariable("chatRecordId") int chatRecordId, @PathVariable("operatorId") int operatorId) {
        try {
            chatRecordService.deleteChatRecord(chatRecordId, operatorId);
            return ResponseEntity.ok("deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            String errMsg = "chat record deleted failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{musicRoomId}/{page}")
    public ResponseEntity<?> getChatRecord(@PathVariable int musicRoomId, @PathVariable("page") int page) {
        try {
            ChatRecordInfo chatRecordInfo = chatRecordService.getAllChatRecord(page, musicRoomId);
            return new ResponseEntity<>(chatRecordInfo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            String errMsg = "chat record get by page failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
