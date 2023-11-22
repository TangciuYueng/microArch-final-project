package cn.edu.tongji.Emotions.service;

import cn.edu.tongji.Emotions.model.UserSongEmotion;
import cn.edu.tongji.Emotions.repository.UserSongEmotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserSongEmotionService {

    private final UserSongEmotionRepository userSongEmotionRepository;

    @Autowired
    public UserSongEmotionService(UserSongEmotionRepository userSongEmotionRepository) {
        this.userSongEmotionRepository = userSongEmotionRepository;
    }

    public List<UserSongEmotion> findAll() {
        return userSongEmotionRepository.findAll();
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