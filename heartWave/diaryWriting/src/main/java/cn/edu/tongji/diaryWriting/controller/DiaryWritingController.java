package cn.edu.tongji.diaryWriting.controller;

import cn.edu.tongji.diaryWriting.model.DiaryEmotion;
import cn.edu.tongji.diaryWriting.dto.AddDiaryWritingRequest;
import cn.edu.tongji.diaryWriting.dto.DiaryWritingInfo;
import cn.edu.tongji.diaryWriting.model.Diary;
import cn.edu.tongji.diaryWriting.service.BaiduSentimentAnalysis;
import cn.edu.tongji.diaryWriting.service.DiaryWritingService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllDiariesByUserId(@PathVariable("userId") int userId) {
        try{
            System.out.println(userId);
            List<Diary> diaries = diaryWritingService.getAllDiariesByUserId(userId);
            return new ResponseEntity<>(diaries, HttpStatusCode.valueOf(HttpStatus.SC_OK));
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("get diaries by user Id failed", HttpStatusCode.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR));
        }
    }

    // 为指定用户创建新的日记（成功）
    @PostMapping
    public ResponseEntity<?> createDiary(@RequestBody AddDiaryWritingRequest diaryRequest) {
        try {
            Date currentDate = new Date();
            // 创建一个格式化日期的对象
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 将日期对象格式化成字符串
            String dateString = df.format(currentDate);
            diaryRequest.setCreateDate(dateString);
            System.out.println(diaryRequest.getCreateDate());
            diaryRequest.setCurrentStatus(1);//1表示发布 2表示尚未发布在草稿箱中等待修改
            int newDiaryId = diaryWritingService.createDiary(diaryRequest);
            return new ResponseEntity<>(newDiaryId, HttpStatusCode.valueOf(HttpStatus.SC_CREATED));
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("add new diary failed", HttpStatusCode.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR));
        }
    }

    //删除指定日记（完成）
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDiaryByID(@PathVariable int id) {
        try{
            int deleteId=diaryWritingService.deleteDiary(id);
            return new ResponseEntity<>(deleteId, HttpStatusCode.valueOf(HttpStatus.SC_OK));
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("delete diary failed", HttpStatusCode.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR));
        }
        // 根据id删除数据的逻辑处理

    }

    //用户修改随笔
    // 更新日记的接口
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDiary(@PathVariable int id, @RequestBody DiaryWritingInfo diary) {
        //如果日记是已发布的状态，那么就把状态改为修改过了
        try {
            Diary previousDiary = diaryWritingService.getDiaryById(id);
//        System.out.println(previousDiary.getCurrentStatus());
//        System.out.println(diary.getCurrentStatus());
            if (diary.getCurrentStatus() == 0) {
                if (previousDiary.getCurrentStatus() == 1 || previousDiary.getCurrentStatus() == 3) {
//            System.out.println(previousDiary.getCurrentStatus());
                    diary.setCurrentStatus(3);
                } else if (previousDiary.getCurrentStatus() == 2) {//如果日记是在草稿箱中未发布，那么还是保持状态2
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
            diaryWritingService.updateDiary(id, diary);
            return new ResponseEntity<>("put diary successfully", HttpStatusCode.valueOf(HttpStatus.SC_OK));
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("put diary failed", HttpStatusCode.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR));
        }
    }

    //获得某个用户的日记总数
    @GetMapping("/count/{userId}")
    public ResponseEntity<?> getDiaryCount(@PathVariable("userId") int userId) {
        try {
            int count = diaryWritingService.diaryCount(userId);
            return new ResponseEntity<>(count, HttpStatusCode.valueOf(HttpStatus.SC_OK));
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("get diaries count failed", HttpStatusCode.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR));
        }
    }


    //根据日记的id获取日记信息
    @GetMapping("/diary/{id}")
    public ResponseEntity<?> getDiaryById(@PathVariable("id") int id) {
        try {
            Diary diary = diaryWritingService.getDiaryById(id);

            // 检查日记是否存在
            if (diary == null) {
                return ResponseEntity.notFound().build();
            }

            return new ResponseEntity<>(diary, HttpStatusCode.valueOf(HttpStatus.SC_OK));
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("get diaries by Id failed", HttpStatusCode.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR));
        }
    }

    //获得指定id日记的情绪分析
    @GetMapping("/emotion-analysis/{id}")
    public ResponseEntity<?> getDiaryEmotion(@PathVariable("id") int id) {
        try{
            //获取日记的id
            Diary diary = diaryWritingService.getDiaryById(id);
            //获取日记的内容
            String content = diary.getContent();
            DiaryEmotion diaryEmotion=baiduSentimentAnalysis.sentimentAnalysis(content);
            // 生成当前时间的 LocalDateTime 对象
            LocalDateTime currentTime = LocalDateTime.now();

            diaryEmotion.setDiaryId(id);
            diaryEmotion.setUserId(diary.getUserId());
            diaryEmotion.setCreateTime(currentTime);
//            SentimentData sentimentData = baiduSentimentAnalysis.sentimentAnalysis(content);
            return new ResponseEntity<>(diaryEmotion,HttpStatusCode.valueOf(HttpStatus.SC_OK));
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("analyze diaries emotion failed", HttpStatusCode.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR));
        }
    }

    //获取当前时间一个小时前新上传的所有日记
    @GetMapping("/recent-diaries")
    public ResponseEntity<?> getRecentDiaries(){
        try{
            List<Diary> recentDiaries=diaryWritingService.getRecentDiaries();
            return new ResponseEntity<>(recentDiaries,HttpStatusCode.valueOf(HttpStatus.SC_OK));
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Get recent diaries failed", HttpStatusCode.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR));
        }
    }

}
