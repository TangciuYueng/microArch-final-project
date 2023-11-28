package cn.edu.tongji.diaryWriting.service;

import cn.edu.tongji.diaryWriting.dto.AddDiaryWritingRequest;
import cn.edu.tongji.diaryWriting.mapper.DiaryWritingMapper;
import cn.edu.tongji.diaryWriting.model.Diary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
// 调用刚刚mapper里的方法去实现里面的业务逻辑
public class DiaryWritingService {
    private final DiaryWritingMapper diaryWritingMapper;

    //根据UserId返回该用户的所有日记
    public List<Diary> getAllDiariesByUserId(int userId) {
        System.out.println(userId);
        System.out.println(diaryWritingMapper.getDiariesByUserId(userId));
        return diaryWritingMapper.getDiariesByUserId(userId);
    }

    //新建日记的时候执行的函数
    public int createDiary(AddDiaryWritingRequest diaryRequest) {

        diaryWritingMapper.insertDiary(diaryRequest);
        return diaryRequest.getId();
    }

    //删除日记的时候执行的函数
    public void deleteDiary(int id, int userId) {
        diaryWritingMapper.deleteDiaryById(id,userId);
    }

    //查询指定用户的指定日记
    public Diary getDiary(int id, int userId){
        return diaryWritingMapper.getDiaryById(id,userId);
    }

}
