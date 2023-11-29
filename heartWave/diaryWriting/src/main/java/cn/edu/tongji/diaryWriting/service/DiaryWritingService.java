package cn.edu.tongji.diaryWriting.service;

import cn.edu.tongji.diaryWriting.dto.AddDiaryWritingRequest;
import cn.edu.tongji.diaryWriting.dto.DiaryWritingInfo;
import cn.edu.tongji.diaryWriting.model.Diary;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface DiaryWritingService {
    List<Diary> getAllDiariesByUserId(int userId);
    int createDiary(AddDiaryWritingRequest diaryRequest);
    int deleteDiary(int id);
    int updateDiary(int id, DiaryWritingInfo diary);
    Diary getDiaryById(int Id);
    int diaryCount(int userId);
}
