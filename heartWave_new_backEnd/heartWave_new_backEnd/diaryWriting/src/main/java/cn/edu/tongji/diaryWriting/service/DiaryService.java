package cn.edu.tongji.diaryWriting.service;

import cn.edu.tongji.diaryWriting.domain.Diary;
import cn.edu.tongji.diaryWriting.dto.DiarySimple;
import cn.edu.tongji.diaryWriting.dto.FriendDiaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface DiaryService {
    /**
     * 获取某用户指定月份的日记简要信息列表。
     *
     * @param userId 用户ID
     * @param year   年份
     * @param month  月份
     * @return 日记简要信息列表
     */
    List<DiarySimple> getDiaryMonthly(Integer userId, int year, int month);

    /**
     * 根据日记ID获取日记条目。
     *
     * @param id 日记ID
     * @return 包含日记条目的Optional对象
     */
    Optional<Diary> getDiaryById(Integer id);

    /**
     * 获取特定用户最近更新的好友日记列表。
     *
     * @param userId      用户ID
     * @param pageRequest 分页请求对象
     * @return 包含好友更新信息（好友ID和日记ID）的分页结果
     */
    Page<FriendDiaryDTO> getFriendUpdateRecently(Integer userId, PageRequest pageRequest);

    /**
     * 查询给定用户的日记数量
     *
     * @param userId 用户ID
     * @return 日记数量
     */
    Integer getDiaryCount(Integer userId);

    /**
     * 保存日记到数据库
     *
     * @param diary 要保存的日记对象
     * @return 成功保存的日记对象
     */
    Diary saveDiary(Diary diary);
}
