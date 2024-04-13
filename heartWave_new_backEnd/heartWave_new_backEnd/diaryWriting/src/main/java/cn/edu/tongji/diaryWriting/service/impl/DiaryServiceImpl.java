package cn.edu.tongji.diaryWriting.service.impl;

import cn.edu.tongji.diaryWriting.domain.Diary;
import cn.edu.tongji.diaryWriting.dto.DiarySimple;
import cn.edu.tongji.diaryWriting.dto.FriendDiaryDTO;
import cn.edu.tongji.diaryWriting.repository.DiaryRepository;
import cn.edu.tongji.diaryWriting.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DiaryServiceImpl implements DiaryService {
    @Autowired
    private DiaryRepository diaryRepository;

    /**
     * 根据用户ID、年份和月份获取当月的日记列表
     *
     * @param userId 用户ID
     * @param year   年份
     * @param month  月份
     * @return 当月的日记简要信息列表
     */
    @Override
    public List<DiarySimple> getDiaryMonthly(Integer userId, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        return diaryRepository.findByUserIdAndCreateDateBetween(userId, startDate, endDate).stream().map(this::convertToDiarySimple).toList();
    }

    /**
     * 根据日记ID获取日记信息
     *
     * @param id 日记ID
     * @return 包含日记信息的 Optional
     */
    @Override
    public Optional<Diary> getDiaryById(Integer id) {
        return diaryRepository.findById(id);
    }

    /**
     * 获取好友最近更新的日记列表
     *
     * @param userId      用户ID
     * @param pageRequest 分页请求参数
     * @return 包含好友最近更新的日记信息的分页数据
     */
    @Override
    public Page<FriendDiaryDTO> getFriendUpdateRecently(Integer userId, PageRequest pageRequest) {
        List<Integer> friendIds = new ArrayList<>();
        friendIds.add(1);
        friendIds.add(5);
        friendIds.add(2);
        return diaryRepository.findFriendsRecentUpdatedDiaries(friendIds, pageRequest);
    }

    /**
     * 查询给定用户的日记数量
     *
     * @param userId 用户ID
     * @return 日记数量
     */
    @Override
    public Integer getDiaryCount(Integer userId) {
        return diaryRepository.getDiaryCount(userId);
    }

    /**
     * 保存日记到数据库
     *
     * @param diary 要保存的日记对象
     * @return 成功保存的日记对象
     */
    @Override
    public Diary saveDiary(Diary diary) {
        return diaryRepository.save(diary);
    }

    /**
     * 将 Diary 对象转换为 DiarySimple 对象
     *
     * @param diary 原始 Diary 对象
     * @return 转换后的 DiarySimple 对象
     */
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
