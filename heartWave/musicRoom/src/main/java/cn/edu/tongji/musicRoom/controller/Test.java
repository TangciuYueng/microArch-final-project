package cn.edu.tongji.musicRoom.controller;

import cn.edu.tongji.musicRoom.dto.*;
import cn.edu.tongji.musicRoom.model.MusicRoom;
import cn.edu.tongji.musicRoom.model.MusicRoomMember;
import cn.edu.tongji.musicRoom.service.ChatRecordService;
import cn.edu.tongji.musicRoom.service.MusicRoomMemberService;
import cn.edu.tongji.musicRoom.service.MusicRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Test {
    @RequestMapping("/test")
    public String test() {
        return "Heart wave!";
    }

    private final MusicRoomService musicRoomService;

    @GetMapping("/api/musicroom/all/{page}")
    public MusicRoomInfo getAllMusicRoom(@PathVariable("page") Integer page) {
        return musicRoomService.getAllMusicRoom(page);
    }

    private final MusicRoomMemberService musicRoomMemberService;
    @GetMapping("/all/{musicRoomId}")
    public List<MusicRoomMember> getAllMusicRoomMember(@PathVariable("musicRoomId") int musicRoomId) {
        return musicRoomMemberService.getMusicRoomMemberByMusicRoomId(musicRoomId);
    }
    @PutMapping("/api/musicroommember/singable")
    public void updateSingable (@RequestBody UpdateSingableRequest request) {
        int userId = request.getUserId();
        int musicRoomId = request.getMusicRoomId();
        boolean singable = request.isSingable();
        if (singable) {
            musicRoomMemberService.setSingable(userId, musicRoomId);
        } else {
            musicRoomMemberService.setNoSingable(userId, musicRoomId);
        }
    }
    @PutMapping("/api/musicroommember/chatable")
    public void updateChatable(@RequestBody UpdateChatableRequest request) {
        int userId = request.getUserId();
        int musicRoomId = request.getMusicRoomId();
        boolean chatable = request.isChatable();
        if (chatable) {
            musicRoomMemberService.setChatable(userId, musicRoomId);
        } else {
            musicRoomMemberService.setNoChatable(userId, musicRoomId);
        }
    }
    @PutMapping("/api/musicroommember/status")
    public void updateStatus(@RequestBody UpdateStatusRequest request) {
        int userId = request.getUserId();
        int musicRoomId = request.getMusicRoomId();
        boolean in = request.isIn();
        if (in) {
            musicRoomMemberService.setStatusIn(userId, musicRoomId);
        } else {
            musicRoomMemberService.setStatusOut(userId, musicRoomId);
        }
    }

    @PostMapping("/api/musicroommember/add")
    public void insertMusicRoomMember(@RequestBody MemberRequest request) {
        int userId = request.getUserId();
        int musicRoomId = request.getMusicRoomId();
        musicRoomMemberService.insertMusicRoomMember(userId, musicRoomId);
    }
    @DeleteMapping("/api/musicroommember/delete")
    public void deleteMusicRoomMember(@RequestBody MemberRequest request) {
        int userId = request.getUserId();
        int musicRoomId = request.getMusicRoomId();
        musicRoomMemberService.deleteMusicRoomMember(userId, musicRoomId);
    }
    private final ChatRecordService chatRecordService;

    //getAllChatRecord方法处理/api/chatrecord/all的GET请求，
    // 接收两个参数：页码page和音乐聊天室IDmusicRoomId，返回一个ChatRecordInfo对象，
    // 该对象包含了指定页码的音乐聊天室的全部聊天记录。
    @GetMapping("/api/chatrecord/all")
    public ChatRecordInfo getAllChatRecord(@RequestParam("page") int page, @RequestParam("musicRoomId") int musicRoomId) {
        return chatRecordService.getAllChatRecord(page, musicRoomId);
    }

    //deleteChatRecord方法处理/api/chatrecord/delete/{chatRecordId}的PUT请求，
    // 接收一个路径参数chatRecordId表示要删除的聊天记录ID，
    // 将该ID对应的聊天记录进行删除操作，并返回一个包含操作结果的ResponseEntity对象。
    @PutMapping("/api/chatrecord/delete/{chatRecordId}")
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
    @PostMapping("/api/chatrecord/add")
    public ResponseEntity<Integer> addChatRecord(@RequestBody AddChatRecordRequest addChatRecordRequest) {
        try {
            int newId = chatRecordService.addChatRecord(addChatRecordRequest);
            return ResponseEntity.ok(newId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(-1);
        }
    }
}
