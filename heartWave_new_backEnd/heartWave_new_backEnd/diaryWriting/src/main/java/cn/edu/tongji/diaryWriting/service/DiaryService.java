package cn.edu.tongji.diaryWriting.service;

import cn.edu.tongji.diaryWriting.domain.Diary;
import cn.edu.tongji.diaryWriting.dto.DiarySimple;

import java.util.List;
import java.util.Optional;

public interface DiaryService {
    List<DiarySimple> getDiaryMonthly(Integer userId, int year, int month);

    Optional<Diary> getDiaryById(Integer id);
}
