package cn.edu.tongji.diaryWriting.controller;

import cn.edu.tongji.diaryWriting.dto.AddDiaryWritingRequest;
import cn.edu.tongji.diaryWriting.dto.DiaryWritingInfo;
import cn.edu.tongji.diaryWriting.dto.SentimentData;
import cn.edu.tongji.diaryWriting.model.Diary;
import cn.edu.tongji.diaryWriting.service.BaiduSentimentAnalysis;
import cn.edu.tongji.diaryWriting.service.DiaryWritingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//对外提供接口
@RestController
@RequestMapping("/api/diary")
@RequiredArgsConstructor
public class DiaryWritingController {
    private final DiaryWritingService diaryWritingService;
    private final BaiduSentimentAnalysis baiduSentimentAnalysis;
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
        Diary previousDiary=diaryWritingService.getDiaryById(id);
//        System.out.println(previousDiary.getCurrentStatus());
//        System.out.println(diary.getCurrentStatus());
        if(diary.getCurrentStatus()==0){
            if(previousDiary.getCurrentStatus()==1 || previousDiary.getCurrentStatus()==3){
//            System.out.println(previousDiary.getCurrentStatus());
                diary.setCurrentStatus(3);
            }
            else if(previousDiary.getCurrentStatus()==2){//如果日记是在草稿箱中未发布，那么还是保持状态2
//            System.out.println(previousDiary.getCurrentStatus());
                diary.setCurrentStatus(2);
            }
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

    //获得某个用户的日记总数
    @GetMapping("/count/{userId}")
    public ResponseEntity<Integer> getDiaryCount(@PathVariable("userId") int userId) {
        int count = diaryWritingService.diaryCount(userId);
        return ResponseEntity.ok().body(count);
    }


    //根据日记的id获取日记信息
    @GetMapping("/diary/{id}")
    public ResponseEntity<?> getDiaryById(@PathVariable("id") int id) {
        Diary diary = diaryWritingService.getDiaryById(id);

        // 检查日记是否存在
        if (diary == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(diary);
    }

    //获得指定id日记的情绪分析
    @GetMapping("/emotionAnalysis/{id}")
    public ResponseEntity<SentimentData> getDiaryEmotion(@PathVariable("id") int id) {
        Diary diary = diaryWritingService.getDiaryById(id);
        String content = diary.getContent();
        SentimentData sentimentData = baiduSentimentAnalysis.sentimentAnalysis(content);
        return ResponseEntity.ok().body(sentimentData);
    }


}
