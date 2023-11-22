package cn.edu.tongji.musicRoom.controller;


import cn.edu.tongji.musicRoom.dto.UpdateChatableRequest;
import cn.edu.tongji.musicRoom.dto.UpdateSingableRequest;
import cn.edu.tongji.musicRoom.dto.UpdateStatusRequest;
import cn.edu.tongji.musicRoom.model.MusicRoomMember;
import cn.edu.tongji.musicRoom.service.MusicRoomMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/musicroommember")
@RequiredArgsConstructor
public class MusicRoomMemberController {
    private final MusicRoomMemberService musicRoomMemberService;
    @GetMapping("/all/{musicRoomId}")
    public List<MusicRoomMember> getAllMusicRoomMember(@PathVariable("musicRoomId") int musicRoomId) {
        return musicRoomMemberService.getMusicRoomMemberByMusicRoomId(musicRoomId);
    }
    @PutMapping("/singable")
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
    @PutMapping("/chatable")
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
    @PutMapping("/status")
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
}
