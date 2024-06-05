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

    /**
     * 根据音乐列表ID获取音乐列表所有信息
     *
     * @param id 音乐列表ID
     * @return 包含音乐列表信息的响应实体
     */
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

    /**
     * 获取多个音乐列表的简单信息
     *
     * @param type    音乐列表类型
     * @param limited 是否限制数量
     * @param userId  用户ID
     * @param page    分页页码
     * @param size    每页大小
     * @return 包含音乐列表的响应实体
     */
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

    /**
     * 根据名称模糊搜索音乐列表较多信息
     *
     * @param name 音乐列表名称
     * @param page 分页页码
     * @param size 每页大小
     * @return 包含音乐列表信息的响应实体
     */
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

    /**
     * 将音乐列表添加到用户 联系集
     * 多对多关系 like/dislike/download
     * 添加 联系集 确保 musicListId 存在
     * 一对多关系 listenRecord/singRecord/recommend/created/admin
     *
     * @param musicListId 音乐列表ID
     * @param userId      用户ID
     * @param type        用户类型
     * @return 包含操作结果的响应实体
     */
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

    /**
     * 获取对应类型音乐列表的用户
     * 多对多关系 like/dislike/download
     * 一对多关系 listenRecord/singRecord/recommend/created/admin
     *
     * @param musicListId 音乐列表ID
     * @param type        用户类型
     * @param page        分页页码
     * @param size        每页大小
     * @return 包含用户信息的响应实体
     */
    @GetMapping("/who-type")
    public ResponseEntity<Result<?>> getUserWhoTypeMusicList(@RequestParam Integer musicListId,
                                                             @RequestParam(defaultValue = "like") String type,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "8") int size) {
        // 校验输入参数
        if (musicListId == null || musicListId <= 0) {
            return ResponseEntity.badRequest().body(new Result<>(400, "Invalid musicListId", null));
        }

        PageRequest pageRequest = PageRequest.of(page, size);

        try {
            Page<Integer> userIdPage = musicListService.getUserWhoTypeMusicList(musicListId, type, pageRequest);

            if (userIdPage != null && !userIdPage.isEmpty()) {
                Result<Page<Integer>> result = new Result<>(200, "Success", userIdPage);
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.ok(new Result<>(404, "No users found for the specified criteria", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Result<>(500, "An error occurred while processing the request", null));
        }
    }

    /**
     * 获取对应用户对应类型音乐列表的数量
     *
     * @param userId 用户ID
     * @param type   音乐类型
     * @return 包含音乐列表数量的响应实体
     */
    @GetMapping("/count")
    public ResponseEntity<Result<Integer>> getTypeMusicListCount(@RequestParam("userId") Integer userId,
                                                                 @RequestParam("type") String type) {
        if (userId == null || type == null) {
            return ResponseEntity.badRequest().body(new Result<>(400, "userId and type parameters are required", null));
        }

        try {
            int count = musicListService.getTypeMusicListCount(userId, type);
            return ResponseEntity.ok(new Result<>(200, "Success", count));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result<>(500, "Failed to retrieve music list count", null));
        }
    }

    /**
     * 更新现有音乐列表
     *
     * @param id        音乐列表ID
     * @param musicList 包含更新信息的音乐列表对象
     * @return 包含更新后音乐列表信息的响应实体
     */
    @PutMapping("/{id}")
    public ResponseEntity<Result<MusicList>> updateMusicList(@PathVariable("id") Integer id, @RequestBody MusicList musicList) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(new Result<>(400, "Invalid music list ID", null));
        }

        if (musicList == null) {
            return ResponseEntity.badRequest().body(new Result<>(400, "Music list object is required", null));
        }

        try {
            Optional<MusicList> existingMusicList = musicListService.getMusicListById(id);

            if (existingMusicList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Result<>(404, "Music list not found with ID: " + id, null));
            }

            musicList.setId(id); // Ensure the music list ID is set to the path variable ID
            MusicList updatedMusicList = musicListService.saveMusicList(musicList);
            return ResponseEntity.ok(new Result<>(200, "Success", updatedMusicList));
        } catch (Exception e) {
            logger.error("Failed to update music list, ID: " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Result<>(500, "Failed to update music list: " + e.getMessage(), null));
        }
    }

    /**
     * 删除现有音乐列表
     *
     * @param id 音乐列表ID
     * @return 包含删除结果的响应实体
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Result<String>> deleteMusicList(@PathVariable("id") Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(new Result<>(400, "Invalid music list ID", null));
        }

        try {
            boolean deleted = musicListService.deleteMusicList(id);
            if (deleted) {
                return ResponseEntity.ok(new Result<>(200, "Success", "Successfully deleted music list with ID: " + id));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Result<>(404, "Music list not found with ID: " + id, null));
            }
        } catch (Exception e) {
            logger.error("Failed to delete music list, ID: " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Result<>(500, "Failed to delete music list: " + e.getMessage(), null));
        }
    }



}
