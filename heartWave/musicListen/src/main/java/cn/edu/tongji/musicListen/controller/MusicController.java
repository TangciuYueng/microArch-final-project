package cn.edu.tongji.musicListen.controller;

import cn.edu.tongji.musicListen.model.Music;
import cn.edu.tongji.musicListen.service.MusicService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/musiclisten/music")
public class MusicController {
    @Resource
    private MusicService musicService;

    @GetMapping
    public List<Music> getAllMusic(){return musicService.getAllMusic();}

    @PostMapping
    public int insertMusic(@RequestBody Music music){
        try {
            System.out.println("contrl" + music);
            return musicService.insertMusic(music);
        } catch (Exception e) {
            // 捕获异常并打印错误信息
            e.printStackTrace();
            // 或者使用日志框架记录错误信息
            // logger.error("Failed to insert music list", e);
            return -1; // 或者根据实际情况返回适当的错误代码
        }
    }
    @PutMapping
    public void updateMusic(Music music){
        musicService.updateMusic(music);
    }

    @DeleteMapping
    public void deleteMusic(int id){
        musicService.deleteMusic(id);
    }


}
