package cn.edu.tongji.diaryWriting.service.impl;

import cn.edu.tongji.diaryWriting.dto.AddDiaryWritingRequest;
import cn.edu.tongji.diaryWriting.dto.DiaryWritingInfo;
import cn.edu.tongji.diaryWriting.mapper.DiaryWritingMapper;
import cn.edu.tongji.diaryWriting.model.Diary;
import cn.edu.tongji.diaryWriting.service.DiaryWritingService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
// 调用刚刚mapper里的方法去实现里面的业务逻辑
public class DiaryWritingServiceImpl implements DiaryWritingService {
    @Resource
    private final DiaryWritingMapper diaryWritingMapper;

    //根据UserId返回该用户的所有日记
    @Override
    public List<Diary> getAllDiariesByUserId(int userId) {
        System.out.println(userId);
        System.out.println(diaryWritingMapper.getDiariesByUserId(userId));
        return diaryWritingMapper.getDiariesByUserId(userId);
    }

    //新建日记的时候执行的函数
    @Override
    public int createDiary(AddDiaryWritingRequest diaryRequest) {
        System.out.println(diaryRequest);
        diaryWritingMapper.insertDiary(diaryRequest);
        return diaryRequest.getUserId();
    }

    //删除日记的时候执行的函数
    @Override
    public int deleteDiary(int id) {
        diaryWritingMapper.deleteDiaryById(id);
        return id;
    }
    //更新日记内容
    @Override
    public int updateDiary(int id, DiaryWritingInfo diary){
        diaryWritingMapper.updateDiary(id,diary);
        return id;
    }
    //根据日记的id返回指定日记
    @Override
    public Diary getDiaryById(int Id) {
//        System.out.println(userId);
//        System.out.println(diaryWritingMapper.getDiariesByUserId(userId));
        return diaryWritingMapper.getDiaryById(Id);
    }

    //得到日记的指定用户日记的总量
    @Override
    public int diaryCount(int userId){
        return diaryWritingMapper.getDiaryCount(userId);
    }

}
