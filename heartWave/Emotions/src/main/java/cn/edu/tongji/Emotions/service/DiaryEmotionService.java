package cn.edu.tongji.Emotions.service;

import cn.edu.tongji.Emotions.model.DiaryEmotion;
import cn.edu.tongji.Emotions.repository.DiaryEmotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiaryEmotionService {

    private final DiaryEmotionRepository diaryEmotionRepository;

    @Autowired
    public DiaryEmotionService(DiaryEmotionRepository diaryEmotionRepository) {
        this.diaryEmotionRepository = diaryEmotionRepository;
    }

    public Page<DiaryEmotion> findAll(Pageable pageable) {
        return diaryEmotionRepository.findAll(pageable);
    }

    public Optional<DiaryEmotion> findById(String id) {
        return diaryEmotionRepository.findById(id);
    }

    public DiaryEmotion save(DiaryEmotion diaryEmotion) {
        return diaryEmotionRepository.save(diaryEmotion);
    }

    public void deleteById(String id) {
        diaryEmotionRepository.deleteById(id);
    }

    // Additional business logic methods can be added here
}