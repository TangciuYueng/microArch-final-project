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
@RequestMapping("/api/musiclisten/playlist")
public class PlaylistController {
    @Resource
    private PlaylistService playlistService;

    @GetMapping
    public List<Playlist> getAllPlaylist(){return playlistService.getAllPlaylist();}

    @PostMapping
    public ResponseEntity<?> insertPlaylist(@RequestBody Playlist playlist){
        try {
            return new ResponseEntity<>(playlistService.insertPlaylist(playlist), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            String errMsg = "insert playlist failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
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
    @DeleteMapping
    public ResponseEntity<?> deletePlaylist(int id){
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
