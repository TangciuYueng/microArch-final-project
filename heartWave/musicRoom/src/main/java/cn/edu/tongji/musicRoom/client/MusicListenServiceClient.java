package cn.edu.tongji.musicRoom.client;

import cn.edu.tongji.musicRoom.dto.MusicRoomSongRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "music-listen-service", path = "/api/music_listen/music")
public interface MusicListenServiceClient {
    @PostMapping("/music_room")
    ResponseEntity<Integer> addMusicRoomSong(@RequestBody MusicRoomSongRequest request);
}
