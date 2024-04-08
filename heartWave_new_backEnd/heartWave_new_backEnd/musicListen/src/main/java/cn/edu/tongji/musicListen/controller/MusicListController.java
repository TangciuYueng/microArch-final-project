package cn.edu.tongji.musicListen.controller;

import cn.edu.tongji.musicListen.common.Result;
import cn.edu.tongji.musicListen.domain.MusicList;
import cn.edu.tongji.musicListen.service.MusicListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/music-listen/music-list")
public class MusicListController {
    private static final Logger logger = LoggerFactory.getLogger(MusicController.class);
    @Autowired
    private MusicListService musicListService;

    @GetMapping("/{id}")
    public ResponseEntity<Result<?>> getMusicListById(@PathVariable("id") Integer id) {
        try {
            if (id <= 0) {
                return ResponseEntity.badRequest().body(new Result<>(400, "ID必须大于0", null));
            }

            Optional<MusicList> optionalMusicList = musicListService.getMusicListById(id);
            if (optionalMusicList.isPresent()) {
                return ResponseEntity.ok(new Result<>(200, "成功", optionalMusicList.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Result<>(404, "未找到ID为 " + id + " 的音乐列表", null));
            }
        } catch (Exception e) {
            logger.error("获取歌单失败，ID：" + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result<>(500, "获取音乐列表信息失败", null));
        }
    }

}
