package cn.edu.tongji.diaryWriting.controller;

import cn.edu.tongji.diaryWriting.dto.AddDiaryWritingRequest;
import cn.edu.tongji.diaryWriting.model.Diary;
import cn.edu.tongji.diaryWriting.service.DiaryWritingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

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

    // 获取指定用户的所有日记列表(成功)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Diary>> getAllDiariesByUserId(@PathVariable("userId") int userId) {
        System.out.println(userId);
        List<Diary> diaries = diaryWritingService.getAllDiariesByUserId(userId);
        return ResponseEntity.ok(diaries);
    }

    // 为指定用户创建新的日记
    @PostMapping("/add")
    public ResponseEntity<Integer> createDiary(@RequestBody AddDiaryWritingRequest diaryRequest) {
        Date currentDate = new Date();

        // 创建一个格式化日期的对象
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 将日期对象格式化成字符串
        String dateString = df.format(currentDate);
        diaryRequest.setCreateDate(dateString);
        System.out.println(diaryRequest.getCreateDate());
        int newDiaryId = diaryWritingService.createDiary(diaryRequest);
        return ResponseEntity.ok(newDiaryId);
    }

}
