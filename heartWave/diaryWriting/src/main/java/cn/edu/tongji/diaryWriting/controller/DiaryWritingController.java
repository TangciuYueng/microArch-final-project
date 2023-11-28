package cn.edu.tongji.diaryWriting.controller;

import cn.edu.tongji.diaryWriting.dto.AddDiaryWritingRequest;
import cn.edu.tongji.diaryWriting.dto.DiaryWritingInfo;
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

    // 为指定用户创建新的日记（成功）
    @PostMapping("/add")
    public ResponseEntity<Integer> createDiary(@RequestBody AddDiaryWritingRequest diaryRequest) {
        Date currentDate = new Date();
        // 创建一个格式化日期的对象
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 将日期对象格式化成字符串
        String dateString = df.format(currentDate);
        diaryRequest.setCreateDate(dateString);
        System.out.println(diaryRequest.getCreateDate());
        diaryRequest.setCurrentStatus(1);//1表示发布 2表示尚未发布在草稿箱中等待修改
        int newDiaryId = diaryWritingService.createDiary(diaryRequest);
        return ResponseEntity.ok(newDiaryId);
    }

    //删除指定日记（完成）
    @DeleteMapping("/delete/{id}")
    public int deleteDiaryByID(@PathVariable int id) {
        // 根据id删除数据的逻辑处理
        return diaryWritingService.deleteDiary(id);
    }

    //用户修改随笔
    // 更新日记的接口
    @PutMapping("/put/{id}")
    public ResponseEntity<String> updateDiary(@PathVariable int id, @RequestBody DiaryWritingInfo diary) {
        //如果日记是已发布的状态，那么就把状态改为修改过了
        if(diary.getCurrentStatus()==1){
            diary.setCurrentStatus(3);
        }
        else if(diary.getCurrentStatus()==2){//如果日记是在草稿箱中未发布，那么还是保持状态2
            diary.setCurrentStatus(2);
        }
        Date currentDate = new Date();
        // 创建一个格式化日期的对象
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 将日期对象格式化成字符串
        String dateString = df.format(currentDate);
        diary.setUpdateTime(dateString);//设置更新时间
        diaryWritingService.updateDiary(id,diary);
        return ResponseEntity.ok("Diary updated successfully.");

    }

}
