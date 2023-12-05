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
public interface DiaryEmotionService {


    Page<DiaryEmotion> findAll(Pageable pageable) ;

    Optional<DiaryEmotion> findById(String id) ;

    public DiaryEmotion save(DiaryEmotion diaryEmotion);

    public void deleteById(String id) ;

}