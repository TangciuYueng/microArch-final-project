package cn.edu.tongji.musicListen.controller;

import cn.edu.tongji.musicListen.model.Music;
import cn.edu.tongji.musicListen.model.MusicComment;
import cn.edu.tongji.musicListen.service.MusicCommentService;
import com.alibaba.nacos.api.naming.pojo.healthcheck.impl.Http;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/musiclisten/musiccomment")

public class MusicCommentController {
    @Resource
    private MusicCommentService musicCommentService;

    @GetMapping
    public ResponseEntity<?> getThisMusicComment(){

        try {
            return new ResponseEntity<>(musicCommentService.getThisMusicComment(), HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
            String errMsg = "get this music comment failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> insertMusicComment(@RequestBody MusicComment musicComment){
        try {
            System.out.println("contrl" + musicComment);
            return new ResponseEntity<>(musicCommentService.insertMusicComment(musicComment),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            String errMsg = "insert music comment failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @DeleteMapping
//    public void deleteMusicComment(int id){
//        musicCommentService.deleteMusicComment(id);
//    }
}
