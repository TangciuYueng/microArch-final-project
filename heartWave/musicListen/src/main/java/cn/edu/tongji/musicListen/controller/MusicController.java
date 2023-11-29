package cn.edu.tongji.musicListen.controller;

import cn.edu.tongji.musicListen.model.Music;
import cn.edu.tongji.musicListen.service.MusicService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/musiclisten/music")
public class MusicController {
    @Resource
    private MusicService musicService;

    //获取所有音乐
    @GetMapping
    public ResponseEntity<?> getAllMusic(){
        try{
            return new ResponseEntity<>(musicService.getAllMusic(),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            String errMsg = "get all music failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //通过音乐id获取一首音乐
    @GetMapping("/{id}")
    public ResponseEntity<?> getMusicById(@PathVariable("id") int id){
        try{
            return new ResponseEntity<>(musicService.getMusicById(id),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            String errMsg = "get music by id failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //获取所有音乐的数量
    @GetMapping("/count")
    public ResponseEntity<?> getAllMusicCount(){
        try{
            return new ResponseEntity<>(musicService.getAllMusicCount(),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            String errMsg = "get the count of music failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity<?> insertMusic(@RequestBody Music music){
        try{
            return new ResponseEntity<>(musicService.insertMusic(music),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            String errMsg = "insert music failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
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


}
