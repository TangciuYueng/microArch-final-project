package cn.edu.tongji.Emotions.service;

import cn.edu.tongji.Emotions.model.Emotion;
import cn.edu.tongji.Emotions.repository.EmotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmotionService {

    private final EmotionRepository emotionRepository;

    @Autowired
    public EmotionService(EmotionRepository emotionRepository) {
        this.emotionRepository = emotionRepository;
    }

    public List<Emotion> findAll() {
        return emotionRepository.findAll();
    }

    public Optional<Emotion> findById(String id) {
        return emotionRepository.findById(id);
    }

    public Emotion save(Emotion emotion) {
        return emotionRepository.save(emotion);
    }

    public void deleteById(String id) {
        emotionRepository.deleteById(id);
    }

    // Additional business logic methods can be added here
}