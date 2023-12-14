package cn.edu.tongji.musicListen.controller;

import cn.edu.tongji.musicListen.dto.MusicInfo;
import cn.edu.tongji.musicListen.dto.MusicRoomSongRequest;
import cn.edu.tongji.musicListen.model.Music;
import cn.edu.tongji.musicListen.service.MusicService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/music_listen/music")
public class MusicController {
    @Resource
    private MusicService musicService;

    //获取所有音乐(分页)
    @GetMapping("/by_page/{page}")
    public ResponseEntity<?> getAllMusic(@PathVariable("page") int page){
        try{
            MusicInfo musicInfo = musicService.getAllMusic(page);
            return new ResponseEntity<>(musicInfo, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            String errMsg = "get all music failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 返回一天内新增的的音乐（需要给推荐微服务）
    @GetMapping("/new")
    public ResponseEntity<?> getNewMusicToday(){
        try{
            return new ResponseEntity<>(musicService.getNewMusicToday(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            String errMsg = "get new music today failed";
            return new ResponseEntity<>(errMsg,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // 通过音乐id获取一首音乐
    @GetMapping("/{id}")
    public ResponseEntity<?> getMusicById(@PathVariable("id") int id){
        try{
            return new ResponseEntity<>(musicService.getMusicById(id), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            String errMsg = "get music by id failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // 通过音乐id播放一首歌
    @CrossOrigin(value = "http://localhost:8888")
    @GetMapping("/play/{id}")
    public ResponseEntity<?> playMusicById(@PathVariable("id") int id){
        try{
            System.out.println(id);
            return new ResponseEntity<>(musicService.playMusic(id), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            String errMsg = "play music by id failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // 获取所有音乐的数量
    @GetMapping("/count")
    public ResponseEntity<?> getAllMusicCount(){
        try{
            return new ResponseEntity<>(musicService.getAllMusicCount(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            String errMsg = "get the count of music failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 添加一首歌
    @PostMapping
    public ResponseEntity<?> insertMusic(@RequestBody Music music){
        try{
            System.out.println("corol:"+ music);
            return new ResponseEntity<>(musicService.insertMusic(music), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            String errMsg = "insert music failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // 更改一首歌的信息
    @PutMapping
    public ResponseEntity<?> updateMusic(@RequestBody Music music){
        try{
            musicService.updateMusic(music);
            return ResponseEntity.ok("update music successfully");
        }catch (Exception e){
            e.printStackTrace();
            String errMsg = "update music failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //通过音乐id来删除一首歌
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMusic(@PathVariable("id") int id){
        try{
            musicService.deleteMusic(id);
            return ResponseEntity.ok("delete music successfully");
        }catch (Exception e){
            e.printStackTrace();
            String errMsg = "delete music failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/music_room")
    public ResponseEntity<?> addMusicRoomSong(@RequestBody MusicRoomSongRequest request) {
        try {
            musicService.addMusicRoomSong(request);
            return ResponseEntity.ok(0);
        } catch (Exception e) {
            return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("download_music")
    public ResponseEntity<?> downloadMusicByName(@RequestParam String filePath) {
        try {
            System.out.println(filePath);
            return new ResponseEntity<>(musicService.downloadAudio(filePath), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
