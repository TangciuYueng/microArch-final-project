package cn.edu.tongji.diaryWriting.repository;

import cn.edu.tongji.diaryWriting.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Integer>, JpaSpecificationExecutor<Diary> {
    List<Diary> findByUserIdAndCreateDateBetween(Integer userId, LocalDate startDate, LocalDate endDate);
}
