package cn.edu.tongji.Emotions.service.impl;

import cn.edu.tongji.Emotions.model.Emotion;
import cn.edu.tongji.Emotions.repository.EmotionRepository;
import cn.edu.tongji.Emotions.service.EmotionService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmotionServiceImpl implements EmotionService {

    @Resource
    EmotionRepository emotionRepository;
    @Autowired
    public EmotionServiceImpl(EmotionRepository emotionRepository) {
        this.emotionRepository = emotionRepository;
    }

    @Override
    public Page<Emotion> findAll(Pageable pageable) {
        return emotionRepository.findAll(pageable);
    }

    @Override
    public Optional<Emotion> findById(String id) {
        return emotionRepository.findById(id);
    }

    @Override
    public Emotion save(Emotion emotion) {
        return emotionRepository.save(emotion);
    }

    @Override
    public void deleteById(String id) {
        emotionRepository.deleteById(id);
    }

    // Additional business logic methods can be added here
}
