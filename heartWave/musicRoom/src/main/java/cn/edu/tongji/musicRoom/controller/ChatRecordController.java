package cn.edu.tongji.musicRoom.controller;

import cn.edu.tongji.musicRoom.dto.AddChatRecordRequest;
import cn.edu.tongji.musicRoom.dto.ChatRecordInfo;
import cn.edu.tongji.musicRoom.service.ChatRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//对外提供的接口
@RestController
@RequestMapping("/api/chatrecord")
@RequiredArgsConstructor
public class ChatRecordController {
    private final ChatRecordService chatRecordService;

    //getAllChatRecord方法处理/api/chatrecord/all的GET请求，
    // 接收两个参数：页码page和音乐聊天室IDmusicRoomId，返回一个ChatRecordInfo对象，
    // 该对象包含了指定页码的音乐聊天室的全部聊天记录。
    @GetMapping("/all")
    public ChatRecordInfo getAllChatRecord(@RequestParam("page") int page, @RequestParam("musicRoomId") int musicRoomId) {
        return chatRecordService.getAllChatRecord(page, musicRoomId);
    }

    //deleteChatRecord方法处理/api/chatrecord/delete/{chatRecordId}的PUT请求，
    // 接收一个路径参数chatRecordId表示要删除的聊天记录ID，
    // 将该ID对应的聊天记录进行删除操作，并返回一个包含操作结果的ResponseEntity对象。
    @PutMapping("/delete/{chatRecordId}")
    public ResponseEntity<String> deleteChatRecord(@PathVariable("chatRecordId") int chatRecordId) {
        try {
            chatRecordService.deleteChatRecord(chatRecordId, true);
            return ResponseEntity.ok("Update successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update");
        }
    }

    //addChatRecord方法处理/api/chatrecord/add的POST请求，
    // 接收一个JSON格式的请求体addChatRecordRequest，
    // 该请求体包含了待添加的聊天记录信息，将该信息添加到数据库中，并返回一个包含新聊天记录ID的ResponseEntity对象。
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
