package cn.edu.tongji.Emotions.service.impl;


import cn.edu.tongji.Emotions.model.DiaryEmotion;
import cn.edu.tongji.Emotions.repository.DiaryEmotionRepository;
import cn.edu.tongji.Emotions.service.DiaryEmotionService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiaryEmotionServiceImpl implements DiaryEmotionService {

    @Resource
    private final DiaryEmotionRepository diaryEmotionRepository;

    public DiaryEmotionServiceImpl(DiaryEmotionRepository diaryEmotionRepository) {
        this.diaryEmotionRepository = diaryEmotionRepository;
    }

    @Override
    public Page<DiaryEmotion> findAll(Pageable pageable) {
        return diaryEmotionRepository.findAll(pageable);
    }

    @Override
    public Optional<DiaryEmotion> findById(String id) {
        return diaryEmotionRepository.findById(id);
    }

    @Override
    public DiaryEmotion save(DiaryEmotion diaryEmotion) {
        return diaryEmotionRepository.save(diaryEmotion);
    }

    @Override
    public void deleteById(String id) {
        diaryEmotionRepository.deleteById(id);
    }

    // Additional business logic methods can be added here
}