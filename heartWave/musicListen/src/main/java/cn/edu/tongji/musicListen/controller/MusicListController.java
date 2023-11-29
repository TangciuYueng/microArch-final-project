package cn.edu.tongji.musicListen.controller;

import cn.edu.tongji.musicListen.model.MusicList;
import cn.edu.tongji.musicListen.service.MusicListService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/musiclisten/musiclist")
public class MusicListController {
//    @Resource
//    private List<MusicList> allMusicList = new ArrayList<>();
    @Resource
    private MusicListService musicListService;
    @PostMapping
    public ResponseEntity<?> insertMusicList(@RequestBody MusicList musicList) {
        try {
            return new ResponseEntity<>(musicListService.insertMusicList(musicList), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            String errMsg = "inset music list failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    @GetMapping
//    public List<MusicList> getAllMusicLists() {
//        return allMusicList;
//    }
}
