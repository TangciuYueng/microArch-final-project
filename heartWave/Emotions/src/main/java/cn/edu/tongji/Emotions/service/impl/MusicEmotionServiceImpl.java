package cn.edu.tongji.Emotions.service.impl;

import cn.edu.tongji.Emotions.model.MusicEmotion;
import cn.edu.tongji.Emotions.repository.MusicEmotionRepository;
import cn.edu.tongji.Emotions.service.MusicEmotionService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MusicEmotionServiceImpl implements MusicEmotionService {

    @Resource
    private final MusicEmotionRepository musicEmotionRepository;

    @Autowired
    public MusicEmotionServiceImpl(MusicEmotionRepository musicEmotionRepository) {
        this.musicEmotionRepository = musicEmotionRepository;
    }

    @Override
    public Page<MusicEmotion> findAll(Pageable pageable) {
        return musicEmotionRepository.findAll(pageable);
    }

    @Override
    public Optional<MusicEmotion> findById(String id) {
        return musicEmotionRepository.findById(id);
    }

    @Override
    public MusicEmotion save(MusicEmotion musicEmotion) {
        return musicEmotionRepository.save(musicEmotion);
    }

    @Override
    public void deleteById(String id) {
        musicEmotionRepository.deleteById(id);
    }

    // Additional business logic methods can be added here
}
