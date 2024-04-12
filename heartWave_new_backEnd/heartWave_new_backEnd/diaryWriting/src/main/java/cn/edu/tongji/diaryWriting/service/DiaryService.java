package cn.edu.tongji.diaryWriting.service;

import cn.edu.tongji.diaryWriting.domain.Diary;
import cn.edu.tongji.diaryWriting.dto.DiarySimple;
import cn.edu.tongji.diaryWriting.dto.FriendDiaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface DiaryService {
    List<DiarySimple> getDiaryMonthly(Integer userId, int year, int month);

    Optional<Diary> getDiaryById(Integer id);

    Page<FriendDiaryDTO> getFriendUpdateRecently(Integer userId, PageRequest pageRequest);
}
