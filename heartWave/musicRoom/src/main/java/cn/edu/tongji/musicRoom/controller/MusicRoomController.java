package cn.edu.tongji.musicRoom.controller;

import cn.edu.tongji.musicRoom.dto.MusicRoomDTO;
import cn.edu.tongji.musicRoom.dto.MusicRoomDetailed;
import cn.edu.tongji.musicRoom.dto.MusicRoomInfo;
import cn.edu.tongji.musicRoom.model.MusicRoom;
import cn.edu.tongji.musicRoom.service.MusicRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/music_room")
public class MusicRoomController {
    private final MusicRoomService musicRoomService;

    @PostMapping
    public ResponseEntity<?> createMusicRoom(@RequestBody MusicRoomDTO musicRoomDTO) {
        try {
            MusicRoom musicRoom = musicRoomService.createMusicRoom(musicRoomDTO);
            return new ResponseEntity<>(musicRoom, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            String errMsg = "create music room failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMusicRoomDetailed(@PathVariable("id") int musicRoomId) {
        try {
            MusicRoomDetailed musicRoomDetailed = musicRoomService.getMusicRoomDetailed(musicRoomId);
            return new ResponseEntity<>(musicRoomDetailed, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
