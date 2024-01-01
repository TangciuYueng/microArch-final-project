package cn.edu.tongji.Emotions.service.impl;


import cn.edu.tongji.Emotions.dto.DiaryEmotionDTO;
import cn.edu.tongji.Emotions.model.DiaryEmotion;
import cn.edu.tongji.Emotions.repository.DiaryEmotionRepository;
import cn.edu.tongji.Emotions.service.DiaryEmotionService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DiaryEmotionServiceImpl implements DiaryEmotionService {

    // DTO转换为实体的方法
    private DiaryEmotion convertToEntity(DiaryEmotionDTO diaryEmotionDTO) {
        DiaryEmotion diaryEmotion = new DiaryEmotion();
        diaryEmotion.setUserId(diaryEmotionDTO.getUserId());
        diaryEmotion.setDiaryId(diaryEmotionDTO.getDiaryId());
        diaryEmotion.setCreateTime(diaryEmotionDTO.getCreateTime());
        diaryEmotion.setSentiment(diaryEmotionDTO.getSentiment());
        diaryEmotion.setConfidence(diaryEmotionDTO.getConfidence());
        diaryEmotion.setPositive(diaryEmotionDTO.getPositive());
        diaryEmotion.setNegative(diaryEmotionDTO.getNegative());
        return diaryEmotion;
    }

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
    public DiaryEmotion save(DiaryEmotionDTO diaryEmotionDTO) {
        // 将 DTO 转换为实体
        DiaryEmotion diaryEmotion = convertToEntity(diaryEmotionDTO);
        // 保存实体到数据库
        return diaryEmotionRepository.save(diaryEmotion);
    }

    @Override
    public void deleteById(int diaryId) {
        diaryEmotionRepository.deleteByDiaryId(diaryId);
    }

    // Additional business logic methods can be added here

    public Optional<Object> findByDiaryId(int diaryId){
        return Optional.ofNullable(diaryEmotionRepository.findByDiaryId(diaryId));
    }

    public List<?> findByUserId(int userId) {
        List<?> diaryEmotions = diaryEmotionRepository.findAllByUserId(userId); // 假设这是返回列表的方法
        if (!diaryEmotions.isEmpty()) {
            return diaryEmotions;
        } else {
            // 处理未找到日记的情况
            return new ArrayList<>();
        }
    }

}