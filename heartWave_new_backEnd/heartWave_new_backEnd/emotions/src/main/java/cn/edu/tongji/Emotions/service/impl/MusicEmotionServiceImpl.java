package cn.edu.tongji.Emotions.service.impl;

import cn.edu.tongji.Emotions.dto.MusicEmotionDTO;
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

    private MusicEmotion convertToEntity(MusicEmotionDTO musicEmotionDTO) {
        MusicEmotion musicEmotion = new MusicEmotion();
        musicEmotion.setId(musicEmotionDTO.getId());
        musicEmotion.setMusicId(musicEmotionDTO.getMusicId());
        musicEmotion.setDramatic(musicEmotionDTO.getDramatic());
        musicEmotion.setAggressive(musicEmotionDTO.getAggressive());
        musicEmotion.setPeace(musicEmotionDTO.getPeace());
        musicEmotion.setHappy(musicEmotionDTO.getHappy());
        musicEmotion.setSad(musicEmotionDTO.getSad());
        // 设置其他属性
        return musicEmotion;
    }


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
    public MusicEmotion save(MusicEmotionDTO musicEmotionDTO) {
        MusicEmotion musicEmotion = convertToEntity(musicEmotionDTO);
        return musicEmotionRepository.save(musicEmotion);
    }

    @Override
    public void deleteById(String id) {
        musicEmotionRepository.deleteById(id);
    }

    @Override
    public Optional<MusicEmotion> findByMusicId(int id) {
        return musicEmotionRepository.findByMusicId(id);
    }

}
