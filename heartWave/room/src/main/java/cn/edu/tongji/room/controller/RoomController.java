package cn.edu.tongji.room.controller;

import cn.edu.tongji.room.dto.CloseRequest;
import cn.edu.tongji.room.dto.MusicRoomDTO;
import cn.edu.tongji.room.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @PutMapping
    private ResponseEntity<?> createRoom(@RequestBody MusicRoomDTO musicRoomDTO) {
        try {
            int port = roomService.createRoom(musicRoomDTO);
            return ResponseEntity.ok(port);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(-1);
        }
    }

    @PostMapping("/chat-rooms/close")
    public void closeChatRoom(@RequestBody CloseRequest request) {
        try {
            roomService.closeChatRoom(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
