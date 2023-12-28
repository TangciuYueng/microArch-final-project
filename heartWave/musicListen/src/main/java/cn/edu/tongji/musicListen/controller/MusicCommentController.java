package cn.edu.tongji.musicListen.controller;


import cn.edu.tongji.musicListen.model.MusicComment;
import cn.edu.tongji.musicListen.service.MusicCommentService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/music-listen/music-comment")
public class MusicCommentController {
    @Resource
    private MusicCommentService musicCommentService;
    // 1.通过音乐id获取一首歌的所有音乐评论
    @GetMapping("/music-id/{musicId}")
    public ResponseEntity<?> getThisMusicComment(@PathVariable("musicId") int id){
        try {
            return new ResponseEntity<>(musicCommentService.selectMusicCommentById(id), HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
            String errMsg = "get this music comment failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // 2.获取某条音乐评论的点赞数量
    @GetMapping("likes-count/{id}")
    public ResponseEntity<?> getCommentLikes(@PathVariable("id") int id){
        try {
            return new ResponseEntity<>(musicCommentService.getCommentLikesCount(id), HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
            String errMsg = "get this music comment likes failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 3.获取一首歌的评论数量
    @GetMapping("/music-count/{musicId}")
    public ResponseEntity<?> getMusicCommentCount(@PathVariable("musicId") int musicId){
        try {
            return new ResponseEntity<>(musicCommentService.getThisMusicCommentCount(musicId), HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
            String errMsg = "get this music comment count failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // 4.为某条音乐评论的点赞数加一个值
    @PutMapping("/{id}/{val}")
    public ResponseEntity<?> addCommentLikes(@PathVariable("id")int id,@PathVariable("val") int val){
        try {
            ResponseEntity chatRecordInfo = musicCommentService.addCommentLikes(id, val);
            return new ResponseEntity<>(chatRecordInfo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            String errMsg = "add music likes failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // 5.给某首歌新增一条音乐评论
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
    // 6.通过音乐评论id删除某条音乐评论
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMusicComment(@PathVariable("id") int id){
        try{
            musicCommentService.deleteMusicComment(id);
            return ResponseEntity.ok("delete music comment successfully");
        }catch (Exception e){
            e.printStackTrace();
            String errMsg = "delete music comment failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
