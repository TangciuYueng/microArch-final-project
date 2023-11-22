package cn.edu.tongji.musicRoom.controller;

import cn.edu.tongji.musicRoom.dto.MusicRoomInfo;
import cn.edu.tongji.musicRoom.model.MusicRoom;
import cn.edu.tongji.musicRoom.service.MusicRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/musicroom")
public class MusicRoomController {
    private final MusicRoomService musicRoomService;
    @PostMapping("/insert")
    public int insertMusicRoom(@RequestBody MusicRoom musicRoom) {
        return musicRoomService.insertMusicRoom(musicRoom);
    }

    @GetMapping("/all/{page}")
    public MusicRoomInfo getAllMusicRoom(@PathVariable("page") Integer page) {
        return musicRoomService.getAllMusicRoom(page);
    }
}
