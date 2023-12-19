package cn.edu.tongji.musicListen.controller;

import cn.edu.tongji.musicListen.mapper.PlayCountMapper;
import cn.edu.tongji.musicListen.model.PlayCount;
import cn.edu.tongji.musicListen.model.Playlist;
import cn.edu.tongji.musicListen.service.PlayCountService;
import cn.edu.tongji.musicListen.service.PlaylistService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/music-listen/play-count")
public class PlayCountController {
    @Resource
    private PlayCountService playCountService;

    // 获取某个用户的音乐播放次数
    @GetMapping("/{userId}")
    public ResponseEntity<?> getPlayCountByUserId(@PathVariable("userId")int userId){
        try {
            return new ResponseEntity<>(playCountService.getListenRecordListByUserId(userId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            String errMsg = "get play count failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 插入一条播放次数记录
    @PostMapping
    public ResponseEntity<?> insertPlaylist(@RequestBody PlayCount playCount){
        try {
            return new ResponseEntity<>(playCountService.insertPlayCount(playCount), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            String errMsg = "insert play count failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
