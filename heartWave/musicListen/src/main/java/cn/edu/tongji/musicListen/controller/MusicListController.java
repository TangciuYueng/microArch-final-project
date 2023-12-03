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
import java.util.Map;

@RestController
@RequestMapping("/api/music_listen/music_list")
public class MusicListController {
//    @Resource
//    private List<MusicList> allMusicList = new ArrayList<>();
    @Resource
    private MusicListService musicListService;
    // 插入一个musicList
    @PostMapping
    public ResponseEntity<?> insertMusicList(@RequestBody MusicList musicList) {
        try {
            return new ResponseEntity<>(musicListService.insertMusicList(musicList), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            String errMsg = "insert music list failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // 通过userid获取该用户创建的musicList
    @GetMapping("/{userId}")
    public  ResponseEntity<?> getMusicListByUserId(@PathVariable("userId") int userId){
        try{
            Map<String, List<MusicList>> musicLists = musicListService.getMusicListByUserId(userId);
            return new ResponseEntity<>(musicLists, HttpStatus.OK);
        } catch (Exception e){
            String errMsg = "get music list failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // 通过userId获取某用户播放记录歌单
    @GetMapping("listen_record/{userId}")
    public  ResponseEntity<?> getListenRecordListByUserId(@PathVariable("userId") int userId){
        try{
            Map<String, List<MusicList>> musicLists = musicListService.getListenRecordListByUserId(userId);
            return new ResponseEntity<>(musicLists, HttpStatus.OK);
        } catch (Exception e){
            String errMsg = "get music list failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
