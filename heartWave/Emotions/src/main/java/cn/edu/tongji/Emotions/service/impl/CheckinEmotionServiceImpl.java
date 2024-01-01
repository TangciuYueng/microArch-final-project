// CheckinEmotionService.java
package cn.edu.tongji.Emotions.service.impl;

import cn.edu.tongji.Emotions.model.CheckinEmotion;
import cn.edu.tongji.Emotions.repository.CheckinEmotionRepository;
import cn.edu.tongji.Emotions.service.CheckinEmotionService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CheckinEmotionServiceImpl implements CheckinEmotionService {

    @Resource
    private final CheckinEmotionRepository checkinEmotionRepository;


    public CheckinEmotionServiceImpl(CheckinEmotionRepository checkinEmotionRepository) {
        this.checkinEmotionRepository = checkinEmotionRepository;
    }

    @Override
    public List<CheckinEmotion> findByCheckinDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();
        return checkinEmotionRepository.findByCheckinTime(startOfDay);
    }

    @Override
    public Page<CheckinEmotion> findByCheckinDates(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        LocalDateTime startOfDay = startDate.atStartOfDay();
        LocalDateTime endOfDay = endDate.plusDays(1).atStartOfDay();
        return checkinEmotionRepository.findByCheckinTimeBetween(startOfDay, endOfDay, pageable);
    }

    @Override
    public List<CheckinEmotion> findByUserId(int userId) {
        return checkinEmotionRepository.findByUserId(userId);
    }


    @Override
    public Page<CheckinEmotion> findAll(Pageable pageable) {
        return checkinEmotionRepository.findAll(pageable);
    }

    @Override
    public Optional<CheckinEmotion> findById(String id) {
        return checkinEmotionRepository.findById(id);
    }

    @Override
    public CheckinEmotion save(CheckinEmotion checkinEmotion) {
        return checkinEmotionRepository.save(checkinEmotion);
    }

    @Override
    public void deleteById(String id) {
        checkinEmotionRepository.deleteById(id);
    }

}