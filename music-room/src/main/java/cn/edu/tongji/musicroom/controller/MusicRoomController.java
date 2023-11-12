package cn.edu.tongji.musicroom.controller;

import cn.edu.tongji.musicroom.dto.MusicRoomInfo.MusicRoomInfo;
import cn.edu.tongji.musicroom.model.MusicRoom;
import cn.edu.tongji.musicroom.service.MusicRoomService;
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
