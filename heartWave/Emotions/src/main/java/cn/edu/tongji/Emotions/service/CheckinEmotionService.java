// CheckinEmotionService.java
package cn.edu.tongji.Emotions.service;

import cn.edu.tongji.Emotions.model.CheckinEmotion;
import cn.edu.tongji.Emotions.repository.CheckinEmotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CheckinEmotionService {

    private final CheckinEmotionRepository checkinEmotionRepository;

    @Autowired
    public CheckinEmotionService(CheckinEmotionRepository checkinEmotionRepository) {
        this.checkinEmotionRepository = checkinEmotionRepository;
    }

    public List<CheckinEmotion> getAllCheckinEmotions() {
        return checkinEmotionRepository.findAll();
    }

    public Optional<CheckinEmotion> getCheckinEmotionById(String id) {
        return checkinEmotionRepository.findById(id);
    }

    public CheckinEmotion saveCheckinEmotion(CheckinEmotion checkinEmotion) {
        return checkinEmotionRepository.save(checkinEmotion);
    }

    public void deleteCheckinEmotion(String id) {
        checkinEmotionRepository.deleteById(id);
    }

}