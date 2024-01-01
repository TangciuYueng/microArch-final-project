package cn.edu.tongji.Emotions.service;

import cn.edu.tongji.Emotions.dto.MusicEmotionDTO;
import cn.edu.tongji.Emotions.model.MusicEmotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface MusicEmotionService {

    public Page<MusicEmotion> findAll(Pageable pageable);

    Optional<MusicEmotion> findById(String id);


    MusicEmotion save(MusicEmotionDTO musicEmotion);

    void deleteById(String id) ;

    Optional<MusicEmotion> findByMusicId(int id);
    // Additional business logic methods can be added here
}