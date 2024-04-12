package cn.edu.tongji.Emotions.service.impl;

import cn.edu.tongji.Emotions.model.UserSongEmotion;
import cn.edu.tongji.Emotions.repository.UserSongEmotionRepository;
import cn.edu.tongji.Emotions.service.UserSongEmotionService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserSongEmotionServiceImpl implements UserSongEmotionService {

    @Resource
    private final UserSongEmotionRepository userSongEmotionRepository;

    @Autowired
    public UserSongEmotionServiceImpl(UserSongEmotionRepository userSongEmotionRepository) {
        this.userSongEmotionRepository = userSongEmotionRepository;
    }

    public Page<UserSongEmotion> findAll(Pageable pageable) {
        return userSongEmotionRepository.findAll(pageable);
    }

    public Optional<UserSongEmotion> findById(String id) {
        return userSongEmotionRepository.findById(id);
    }

    public UserSongEmotion save(UserSongEmotion userSongEmotion) {
        return userSongEmotionRepository.save(userSongEmotion);
    }

    public void deleteById(String id) {
        userSongEmotionRepository.deleteById(id);
    }

    // Additional business logic methods can be added here

}
