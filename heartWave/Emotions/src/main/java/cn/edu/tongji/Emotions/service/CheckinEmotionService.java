// CheckinEmotionService.java
package cn.edu.tongji.Emotions.service;

import cn.edu.tongji.Emotions.model.CheckinEmotion;
import cn.edu.tongji.Emotions.repository.CheckinEmotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public interface CheckinEmotionService {


    public List<CheckinEmotion> findByCheckinDate(LocalDate date) ;

    public Page<CheckinEmotion> findByCheckinDates(LocalDate startDate, LocalDate endDate, Pageable pageable);


    public Optional<CheckinEmotion> findByUserId(String UserId) ;

    public Page<CheckinEmotion> findAll(Pageable pageable);


    public Optional<CheckinEmotion> findById(String id);

    public CheckinEmotion save(CheckinEmotion checkinEmotion);

    public void deleteById(String id);

}