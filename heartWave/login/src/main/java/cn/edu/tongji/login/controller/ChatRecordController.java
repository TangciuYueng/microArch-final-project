package cn.edu.tongji.login.controller;

import cn.edu.tongji.login.dto.AddChatRecordRequest;
import cn.edu.tongji.login.dto.GetChatRecordPageByTimeRequest;
import cn.edu.tongji.login.dto.GetChatRecordPageCountByDateRequest;
import cn.edu.tongji.login.dto.GetChatRecordPageByDateRequest;
import cn.edu.tongji.login.model.ChatRecord;
import cn.edu.tongji.login.service.ChatRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat-record")
@RequiredArgsConstructor
public class ChatRecordController {
    private final ChatRecordService chatRecordService;

    @PostMapping("/add")
    public int addChatRecord(@RequestBody AddChatRecordRequest addChatRecordRequest) {
        return chatRecordService.addChatRecord(addChatRecordRequest);
    }

    @PutMapping("/delete/{id}")
    public int deleteChatRecord(@PathVariable("id") int id) {
        return chatRecordService.deleteChatRecord(id);
    }

    @GetMapping("/get/page-count/date")
    public int getChatRecordPageCountByDate(@RequestBody GetChatRecordPageCountByDateRequest getChatRecordPageCountByDateRequest) {
        return chatRecordService.getChatRecordPageCountByDate(getChatRecordPageCountByDateRequest);
    }

    @GetMapping("/get/page/date")
    public ResponseEntity<List<ChatRecord>> getChatRecordByPage(@RequestBody GetChatRecordPageByDateRequest getChatRecordPageByDateRequest) {
        return ResponseEntity.ok(chatRecordService.getChatRecordByPage(getChatRecordPageByDateRequest));
    }

    @GetMapping("/get/page/time")
    public ResponseEntity<List<ChatRecord>> getChatRecordByTime(@RequestBody GetChatRecordPageByTimeRequest getChatRecordPageByTimeRequest) {
        return ResponseEntity.ok(chatRecordService.getChatRecordByTime(getChatRecordPageByTimeRequest));
    }
}
