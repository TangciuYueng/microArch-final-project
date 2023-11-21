package cn.edu.tongji.musicRoom.controller;

import cn.edu.tongji.musicRoom.dto.AddChatRecordRequest;
import cn.edu.tongji.musicRoom.dto.ChatRecordInfo;
import cn.edu.tongji.musicRoom.service.ChatRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chatrecord")
@RequiredArgsConstructor
public class ChatRecordController {
    private final ChatRecordService chatRecordService;
    @GetMapping("/all")
    public ChatRecordInfo getAllChatRecord(@RequestParam("page") int page, @RequestParam("musicRoomId") int musicRoomId) {
        return chatRecordService.getAllChatRecord(page, musicRoomId);
    }
    @PutMapping("/delete/{chatRecordId}")
    public ResponseEntity<String> deleteChatRecord(@PathVariable("chatRecordId") int chatRecordId) {
        try {
            chatRecordService.deleteChatRecord(chatRecordId, true);
            return ResponseEntity.ok("Update successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update");
        }
    }
    @PostMapping("/add")
    public ResponseEntity<Integer> addChatRecord(@RequestBody AddChatRecordRequest addChatRecordRequest) {
        try {
            int newId = chatRecordService.addChatRecord(addChatRecordRequest);
            return ResponseEntity.ok(newId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(-1);
        }
    }
}
