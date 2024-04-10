package cn.edu.tongji.diaryWriting.service.impl;

import cn.edu.tongji.diaryWriting.domain.Diary;
import cn.edu.tongji.diaryWriting.dto.DiarySimple;
import cn.edu.tongji.diaryWriting.repository.DiaryRepository;
import cn.edu.tongji.diaryWriting.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DiaryServiceImpl implements DiaryService {
    @Autowired
    private DiaryRepository diaryRepository;
    @Override
    public List<DiarySimple> getDiaryMonthly(Integer userId, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        return diaryRepository.findByUserIdAndCreateDateBetween(userId, startDate, endDate).stream().map(this::convertToDiarySimple).toList();
    }

    @Override
    public Optional<Diary> getDiaryById(Integer id) {
        return diaryRepository.findById(id);
    }

    private DiarySimple convertToDiarySimple(Diary diary) {
        return DiarySimple.builder()
                .id(diary.getId())
                .currentStatus(diary.getCurrentStatus())
                .createDate(diary.getCreateDate())
                .updateTime(diary.getUpdateTime())
                .title(diary.getTitle())
                .cover(diary.getCover())
                .permission(diary.getPermission())
                .build();
    }
}
