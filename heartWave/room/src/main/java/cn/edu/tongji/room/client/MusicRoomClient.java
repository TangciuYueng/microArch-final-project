package cn.edu.tongji.room.client;

import cn.edu.tongji.room.dto.MusicRoomDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "music-room-service", path = "/api/music_room")
public interface MusicRoomClient {
    @PostMapping
    ResponseEntity<?> createMusicRoom(@RequestBody MusicRoomDTO musicRoomDTO);
    @PutMapping("/close")
    ResponseEntity<?> closeMusicRoom(@RequestParam("musicRoomId") int musicRoomId, @RequestParam("operatorId") int operatorId);
}
