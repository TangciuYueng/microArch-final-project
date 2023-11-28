package cn.edu.tongji.diaryWriting.controller;

import cn.edu.tongji.diaryWriting.dto.addDiaryWritingRequest;
import cn.edu.tongji.diaryWriting.model.diary;
import cn.edu.tongji.diaryWriting.service.DiaryWritingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//对外提供接口
@RestController
@RequestMapping("/api/diary")
@RequiredArgsConstructor
public class DiaryWritingController {
    private final DiaryWritingService diaryWritingService;

    //测试接口是否可以使用，事实证明通过http://localhost:8881/api/diary/test就可以获取到Heart wave!
    @RequestMapping("/test")
    public String test() {
        return "Heart wave!";
    }

    // 获取指定用户的所有日记列表
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<diary>> getAllDiariesByUserId(@PathVariable("userId") int userId) {
        System.out.println(userId);
        List<diary> diaries = diaryWritingService.getAllDiariesByUserId(userId);
        return ResponseEntity.ok(diaries);
    }

    // 为指定用户创建新的日记
    @PostMapping("/add/{userId}")
    public ResponseEntity<Integer> createDiary(@RequestBody addDiaryWritingRequest diaryRequest) {
        int newDiaryId = diaryWritingService.createDiary(diaryRequest);
        return ResponseEntity.ok(newDiaryId);
    }

}
