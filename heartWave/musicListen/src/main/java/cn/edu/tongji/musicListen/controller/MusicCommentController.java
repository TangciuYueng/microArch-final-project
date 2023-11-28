package cn.edu.tongji.musicListen.controller;

import cn.edu.tongji.musicListen.model.Music;
import cn.edu.tongji.musicListen.model.MusicComment;
import cn.edu.tongji.musicListen.service.MusicCommentService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/musiclisten/musiccomment")

public class MusicCommentController {
    @Resource
    private MusicCommentService musicCommentService;

    @GetMapping
    public List<Music> getThisMusicComment(){return musicCommentService.getThisMusicComment();}

    @PostMapping
    public int insertMusicComment(@RequestBody MusicComment musicComment){
        try {
            System.out.println("contrl" + musicComment);
            return musicCommentService.insertMusicComment(musicComment);
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // 或者根据实际情况返回适当的错误代码
        }
    }

//    @DeleteMapping
//    public void deleteMusicComment(int id){
//        musicCommentService.deleteMusicComment(id);
//    }
}
