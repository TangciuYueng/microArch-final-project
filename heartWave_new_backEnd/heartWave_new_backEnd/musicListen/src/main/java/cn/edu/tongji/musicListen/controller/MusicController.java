package cn.edu.tongji.musicListen.controller;

import cn.edu.tongji.musicListen.common.Result;
import cn.edu.tongji.musicListen.domain.Music;
import cn.edu.tongji.musicListen.service.MusicService;
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
@RequestMapping("/api/v1/music-listen/music")
public class MusicController {
    private static final Logger logger = LoggerFactory.getLogger(MusicController.class);
    @Autowired
    private MusicService musicService;

    @GetMapping("/test")
    String testApi() {
        return "hello madre mia!";
    }

    /**
     * @return Music
     * @功能描述：根据id查询 music
     * @author TangciuYueng
     */
    @GetMapping("/{id}")
    public ResponseEntity<Result<?>> getMusicById(@PathVariable("id") Integer id) {
        try {
            if (id <= 0) {
                return ResponseEntity.badRequest().body(new Result<>(400, "ID必须大于0", null));
            }
            Optional<Music> optionalMusic = musicService.getMusicById(id);
            return optionalMusic.<ResponseEntity<Result<?>>>map(music -> ResponseEntity.ok(new Result<>(200, "成功", music))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result<>(404, "未找到ID为 " + id + " 的音乐", null)));
        } catch (Exception e) {
            logger.error("获取音乐失败，ID：" + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result<>(500, "获取音乐信息失败", null));
        }
    }

    @GetMapping
    public ResponseEntity<Result<Page<Music>>> getAllMusics(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {

        Result<Page<Music>> result;
        try {
            if (page < 0 || size <= 0) {
                throw new IllegalArgumentException("Invalid page or size parameters");
            }

            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Music> musicPage = musicService.findAllByPage(pageRequest);

            result = new Result<>(200, "Success", musicPage);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            result = new Result<>(400, "Bad Request", null);
            return ResponseEntity.badRequest().body(result);
        } catch (Exception e) {
            logger.error("Failed to get all musics", e);
            result = new Result<>(500, "Internal Server Error", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @GetMapping("/title")
    public ResponseEntity<Result<?>> searchMusicByTitle(@RequestParam String title,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "15") int size) {
        Result<Page<Music>> result;
        try {
            if (page < 0 || size <= 0) {
                throw new IllegalArgumentException("Invalid page or size parameters");
            }

            PageRequest pageRequest;
            pageRequest = PageRequest.of(page, size);
            Page<Music> musicPage = musicService.findMusicByTitle(pageRequest, title);

            result = new Result<>(200, "Success", musicPage);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            result = new Result<>(400, "Bad Request", null);
            return ResponseEntity.badRequest().body(result);
        } catch (Exception e) {
            logger.error("Failed to get music", e);
            result = new Result<>(500, "Internal Server Error", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
}
