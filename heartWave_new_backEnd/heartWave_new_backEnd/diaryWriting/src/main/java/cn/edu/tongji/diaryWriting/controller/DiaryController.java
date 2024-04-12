package cn.edu.tongji.diaryWriting.controller;

import cn.edu.tongji.diaryWriting.common.Result;
import cn.edu.tongji.diaryWriting.domain.Diary;
import cn.edu.tongji.diaryWriting.dto.DiarySimple;
import cn.edu.tongji.diaryWriting.service.DiaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/diary-writing/diary")
public class DiaryController {
    @Autowired
    private DiaryService diaryService;

    private static final Logger logger = LoggerFactory.getLogger(DiaryController.class);

    @GetMapping("/{id}")
    public ResponseEntity<Result<?>> getDiaryById(@PathVariable("id") Integer id) {
        try {
            if (id <= 0) {
                return ResponseEntity.badRequest().body(new Result<>(400, "ID必须大于0", null));
            }
            Optional<Diary> optionalDiary = diaryService.getDiaryById(id);
            return optionalDiary.<ResponseEntity<Result<?>>>map(diary -> ResponseEntity.ok(new Result<>(200, "成功", diary))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result<>(404, "未找到ID为 " + id + " 的音乐", null)));
        } catch (Exception e) {
            logger.error("获取日记失败，ID：" + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result<>(500, "获取音乐信息失败", null));
        }
    }

    @GetMapping("/monthly")
    public ResponseEntity<Result<?>> getDiaryMonthly(@RequestParam("usrId") Integer userId,
                                                     @RequestParam("year") int year,
                                                     @RequestParam("month") int month) {
        Result<List<DiarySimple>> result;
        try {
            if (userId == null || year <= 0 || month < 1 || month > 12) {
                throw new IllegalArgumentException("Invalid input parameters");
            }

            List<DiarySimple> diaryList = diaryService.getDiaryMonthly(userId, year, month);

            result = new Result<>(200, "Success", diaryList);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            result = new Result<>(400, "Bad Request", null);
            return ResponseEntity.badRequest().body(result);
        } catch (Exception e) {
            logger.error("Failed to get diary", e);
            result = new Result<>(500, "Internal Server Error", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @GetMapping("/friend/update-recently")
    public ResponseEntity<Result<?>> getFriendUpdateRecently(@RequestParam Integer userId,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "5") int size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        return ResponseEntity.ok(new Result<>(200, "Success", diaryService.getFriendUpdateRecently(userId, pageRequest)));
    }
}
