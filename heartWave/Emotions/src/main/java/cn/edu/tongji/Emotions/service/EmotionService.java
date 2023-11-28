package cn.edu.tongji.Emotions.service;

import cn.edu.tongji.Emotions.model.Emotion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public interface EmotionService {


    Page<Emotion> findAll(Pageable pageable);

    Optional<Emotion> findById(String id);

    Emotion save(Emotion emotion) ;

    void deleteById(String id);

    // Additional business logic methods can be added here
}