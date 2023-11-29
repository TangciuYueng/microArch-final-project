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

    //获取所有音乐
    @GetMapping
    public List<Music> getAllMusic(){return musicService.getAllMusic();}
    //通过音乐id获取一首音乐
    @GetMapping("/{id}")
    public Music getMusicById(@PathVariable("id") int id){
        return musicService.getMusicById(id);
    }
    //获取所有音乐的数量
    @GetMapping("/count")
    public int getAllMusicCount(){
        return musicService.getAllMusicCount();
    }


    @PostMapping
    public int insertMusic(@RequestBody Music music){
        try {
            System.out.println("contrl" + music);
            return musicService.insertMusic(music);
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // 或者根据实际情况返回适当的错误代码
        }
    }
    @PutMapping
    public void updateMusic(@RequestBody Music music){
        musicService.updateMusic(music);
    }

    //通过音乐id来删除一首歌
    @DeleteMapping("/{id}")
    public void deleteMusic(@PathVariable("id") int id){
        musicService.deleteMusic(id);
    }


}
