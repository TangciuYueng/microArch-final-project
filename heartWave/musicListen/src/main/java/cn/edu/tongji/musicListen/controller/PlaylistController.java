package cn.edu.tongji.musicListen.controller;

import cn.edu.tongji.musicListen.model.Playlist;
import cn.edu.tongji.musicListen.service.PlaylistService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
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
    public int insertPlaylist(@RequestBody Playlist playlist){
        try {
            System.out.println("contrl" + playlist);
            return playlistService.insertPlaylist(playlist);
        } catch (Exception e) {
            // 捕获异常并打印错误信息
            e.printStackTrace();
            // 或者使用日志框架记录错误信息
            // logger.error("Failed to insert playlist list", e);
            return -1; // 或者根据实际情况返回适当的错误代码
        }
    }
    @PutMapping
    public void updatePlaylist(Playlist playlist){
        playlistService.updatePlaylist(playlist);
    }

    @DeleteMapping
    public void deletePlaylist(int id){
        playlistService.deletePlaylist(id);
    }
}
