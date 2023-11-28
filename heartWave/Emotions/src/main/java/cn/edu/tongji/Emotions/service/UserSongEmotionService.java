package cn.edu.tongji.Emotions.service;

import cn.edu.tongji.Emotions.model.UserSongEmotion;
import cn.edu.tongji.Emotions.repository.UserSongEmotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserSongEmotionService {

    public Page<UserSongEmotion> findAll(Pageable pageable) ;
    public Optional<UserSongEmotion> findById(String id) ;

    public UserSongEmotion save(UserSongEmotion userSongEmotion) ;

    public void deleteById(String id);

    // Additional business logic methods can be added here
}