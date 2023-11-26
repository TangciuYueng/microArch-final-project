// CheckinEmotionService.java
package cn.edu.tongji.Emotions.service;

import cn.edu.tongji.Emotions.model.CheckinEmotion;
import cn.edu.tongji.Emotions.repository.CheckinEmotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CheckinEmotionService {

    private final CheckinEmotionRepository checkinEmotionRepository;

    @Autowired
    public CheckinEmotionService(CheckinEmotionRepository checkinEmotionRepository) {
        this.checkinEmotionRepository = checkinEmotionRepository;
    }

    public List<CheckinEmotion> findByCheckinDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();
        return checkinEmotionRepository.findByCheckinTimeBetween(startOfDay, endOfDay);
    }

    public List<CheckinEmotion> findByCheckinDates(LocalDate startDate,LocalDate endDate) {
        LocalDateTime startOfDay = startDate.atStartOfDay();
        LocalDateTime endOfDay = endDate.atStartOfDay();
        return checkinEmotionRepository.findByCheckinTimeBetween(startOfDay, endOfDay);
    }

    public Optional<CheckinEmotion> findByUserId(String UserId) {
        return checkinEmotionRepository.findByUserId(UserId);
    }

    public List<CheckinEmotion> findAll() {
        return checkinEmotionRepository.findAll();
    }

    public Optional<CheckinEmotion> findById(String id) {
        return checkinEmotionRepository.findById(id);
    }

    public CheckinEmotion save(CheckinEmotion checkinEmotion) {
        return checkinEmotionRepository.save(checkinEmotion);
    }

    public void deleteById(String id) {
        checkinEmotionRepository.deleteById(id);
    }

}