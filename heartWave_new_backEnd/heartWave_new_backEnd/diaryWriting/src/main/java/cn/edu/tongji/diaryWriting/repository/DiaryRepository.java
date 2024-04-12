package cn.edu.tongji.diaryWriting.repository;

import cn.edu.tongji.diaryWriting.domain.Diary;
import cn.edu.tongji.diaryWriting.dto.FriendDiaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Integer>, JpaSpecificationExecutor<Diary> {
    /**
     * 查询对应用户在指定日期范围内的所有日记信息
     *
     * @param userId    用户ID
     * @param startDate 起始日期
     * @param endDate   结束日期
     * @return 符合条件的日记列表
     */
    List<Diary> findByUserIdAndCreateDateBetween(Integer userId, LocalDate startDate, LocalDate endDate);

    /**
     * 查询最近更新日记的好友及其日记信息
     *
     * @param friendIds   好友ID列表
     * @param pageRequest 分页请求对象
     * @return 分页查询结果，包含好友ID与日记ID的 DTO 对象列表
     */
    @Query("SELECT new cn.edu.tongji.diaryWriting.dto.FriendDiaryDTO(d.userId as friendId, d.id as diaryId) FROM Diary d WHERE d.userId IN :friendIds ORDER BY d.updateTime DESC")
    Page<FriendDiaryDTO> findFriendsRecentUpdatedDiaries(@Param("friendIds") List<Integer> friendIds, PageRequest pageRequest);
}
