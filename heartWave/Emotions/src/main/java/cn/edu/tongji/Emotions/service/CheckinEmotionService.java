// CheckinEmotionService.java
package cn.edu.tongji.Emotions.service;

import cn.edu.tongji.Emotions.model.CheckinEmotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public interface CheckinEmotionService {

    List<CheckinEmotion> findByCheckinDate(LocalDate date) ;
    Page<CheckinEmotion> findByCheckinDates(LocalDate startDate, LocalDate endDate, Pageable pageable);
    List<CheckinEmotion> findByUserId(int userId);
    Page<CheckinEmotion> findAll(Pageable pageable);
    Optional<CheckinEmotion> findById(String id);
    CheckinEmotion save(CheckinEmotion checkinEmotion);
    void deleteById(String id);
}