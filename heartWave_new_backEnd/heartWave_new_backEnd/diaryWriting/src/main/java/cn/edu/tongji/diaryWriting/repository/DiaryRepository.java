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
    List<Diary> findByUserIdAndCreateDateBetween(Integer userId, LocalDate startDate, LocalDate endDate);
    @Query("SELECT new cn.edu.tongji.diaryWriting.dto.FriendDiaryDTO(d.userId as friendId, d.id as diaryId) FROM Diary d WHERE d.userId IN :friendIds ORDER BY d.updateTime DESC")
    Page<FriendDiaryDTO> findFriendsRecentUpdatedDiaries(@Param("friendIds") List<Integer> friendIds, PageRequest pageRequest);
}
