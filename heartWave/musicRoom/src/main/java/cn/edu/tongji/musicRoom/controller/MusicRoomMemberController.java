package cn.edu.tongji.musicRoom.controller;


import cn.edu.tongji.musicRoom.dto.*;
import cn.edu.tongji.musicRoom.model.MusicRoomMember;
import cn.edu.tongji.musicRoom.service.MusicRoomMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/music_room_member")
@RequiredArgsConstructor
public class MusicRoomMemberController {
    private final MusicRoomMemberService musicRoomMemberService;
    @PostMapping
    public ResponseEntity<?> addMusicRoomMember(@RequestBody MemberRequest request) {
        try {
            MusicRoomMember musicRoomMember = musicRoomMemberService.addMusicRoomMember(request);
            return new ResponseEntity<>(musicRoomMember, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            String errMsg = "add music room member failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{memberId}/singable")
    public ResponseEntity<?> updateMusicRoomMemberSingable(@PathVariable("memberId") int memberId, @RequestBody MemberSingableRequest request) {
        try {
            musicRoomMemberService.updateMemberSingable(memberId, request);
            return ResponseEntity.ok("successfully updated singable");
        } catch (Exception e) {
            e.printStackTrace();
            String errMsg = "update member singable failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{memberId}/chatable")
    public ResponseEntity<?> updateMusicRoomMemberChatable(@PathVariable("memberId") int memberId, @RequestBody MemberChatableRequest request) {
        try {
            musicRoomMemberService.updateMemberChatable(memberId, request);
            return ResponseEntity.ok("successfully updated chatable");
        } catch (Exception e) {
            e.printStackTrace();
            String errMsg = "update member chatable failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{memberId}/status/{in}")
    public ResponseEntity<?> updateMusicRoomMemberStatus(@PathVariable("memberId") int memberId, @PathVariable("in") boolean in) {
        try {
            musicRoomMemberService.updateMemberStatus(memberId, in);
            return ResponseEntity.ok("successful update status");
        } catch (Exception e) {
            e.printStackTrace();
            String errMsg = "update member status failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
