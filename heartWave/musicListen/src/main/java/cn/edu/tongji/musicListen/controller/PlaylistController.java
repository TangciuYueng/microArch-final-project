package cn.edu.tongji.musicListen.controller;

import cn.edu.tongji.musicListen.model.Playlist;
import cn.edu.tongji.musicListen.service.PlaylistService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/music_listen/play_list")
public class PlaylistController {
    @Resource
    private PlaylistService playlistService;

    // 获取所有的playlist数据
    @GetMapping
    public ResponseEntity<?> getAllPlaylist(){
        try {
            return new ResponseEntity<>(playlistService.getAllPlaylist(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            String errMsg = "insert playlist failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // 插入一条playlist
    @PostMapping
    public ResponseEntity<?> insertPlaylist(@RequestBody Playlist playlist){
        try {
            System.out.println("contrl" + playlist);
            return new ResponseEntity<>(playlistService.insertPlaylist(playlist), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            String errMsg = "insert playlist failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // 更新一条playlist的数据
    @PutMapping
    public ResponseEntity<?> updatePlaylist(@RequestBody Playlist playlist){
        try {
            playlistService.updatePlaylist(playlist);
            return ResponseEntity.ok("update playlist successfully");
        } catch (Exception e) {
            e.printStackTrace();
            String errMsg = "update playlist failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    // 用playlist的id来删除一条playlist
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlaylist(@PathVariable("id")int id){
        try {
            playlistService.deletePlaylist(id);
            return ResponseEntity.ok("delete playlist successfully");
        } catch (Exception e) {
            e.printStackTrace();
            String errMsg = "delete playlist failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
