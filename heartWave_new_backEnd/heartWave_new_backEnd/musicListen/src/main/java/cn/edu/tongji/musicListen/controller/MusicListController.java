package cn.edu.tongji.musicListen.controller;

import cn.edu.tongji.musicListen.common.Result;
import cn.edu.tongji.musicListen.domain.MusicList;
import cn.edu.tongji.musicListen.dto.MusicListMedium;
import cn.edu.tongji.musicListen.dto.MusicListSimple;
import cn.edu.tongji.musicListen.service.MusicListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
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
            return optionalMusicList.<ResponseEntity<Result<?>>>map(musicList -> ResponseEntity.ok(new Result<>(200, "成功", musicList))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result<>(404, "未找到ID为 " + id + " 的音乐列表", null)));
        } catch (Exception e) {
            logger.error("获取歌单失败，ID：" + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result<>(500, "获取音乐列表信息失败", null));
        }
    }

    @GetMapping("/{type}/{limited}")
    public ResponseEntity<Result<?>> getMusicList(@PathVariable("type") String type,
                                                  @PathVariable("limited") Boolean limited,
                                                  @RequestParam(defaultValue = "-1") Integer userId,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "3") int size) {
        Result<Page<MusicListSimple>> result;
        try {
            if (page < 0 || size <= 0) {
                throw new IllegalArgumentException("Invalid page or size parameters");
            }

            PageRequest pageRequest;
            // 限制之下 返回前三个
            if (limited) {
                pageRequest = PageRequest.of(0, 3);
            } else {
                pageRequest = PageRequest.of(page, size);
            }
            Page<MusicListSimple> musicListSimplePage = musicListService.findMusicListSimple(pageRequest, type, userId);

            result = new Result<>(200, "Success", musicListSimplePage);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            result = new Result<>(400, "Bad Request", null);
            return ResponseEntity.badRequest().body(result);
        } catch (Exception e) {
            logger.error("Failed to get music list", e);
            result = new Result<>(500, "Internal Server Error", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @GetMapping("/name")
    public ResponseEntity<Result<?>> searchMusicListByName(@RequestParam String name,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "15") int size) {
        Result<Page<MusicListMedium>> result;
        try {
            if (page < 0 || size <= 0) {
                throw new IllegalArgumentException("Invalid page or size parameters");
            }

            PageRequest pageRequest;
            pageRequest = PageRequest.of(page, size);
            Page<MusicListMedium> musicListMediumPage = musicListService.findMusicListByName(pageRequest, name);

            result = new Result<>(200, "Success", musicListMediumPage);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            result = new Result<>(400, "Bad Request", null);
            return ResponseEntity.badRequest().body(result);
        } catch (Exception e) {
            logger.error("Failed to get music list", e);
            result = new Result<>(500, "Internal Server Error", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @PostMapping
    public ResponseEntity<Result<String>> addMusicListToUser(@RequestParam Integer musicListId,
                                                             @RequestParam Integer userId,
                                                             @RequestParam String type) {
        try {
            if (!isValidType(type)) {
                return ResponseEntity.badRequest().body(new Result<>(400, "Bad Request", "Invalid 'type' parameter."));
            }

            musicListService.addMusicListToUser(musicListId, userId, type);

            Result<String> result = new Result<>(200, "Success", "MusicList added to user successfully!");
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new Result<>(400, "Bad Request", "Bad request: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result<>(500, "Internal Server Error", "Failed to add MusicList to user: " + e.getMessage()));
        }
    }

    private boolean isValidType(String type) {
        List<String> validTypes = Arrays.asList("normal", "like", "dislike", "listenRecord", "singRecord", "recommend", "created", "admin", "download", "album");
        return validTypes.contains(type);
    }
}
