package cn.edu.tongji.Emotions.service.impl;

import cn.edu.tongji.Emotions.model.CheckinEmotion;
import cn.edu.tongji.Emotions.repository.CheckinEmotionRepository;
import cn.edu.tongji.Emotions.service.CheckinEmotionService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CheckinEmotionServiceImpl implements CheckinEmotionService {

    private static final Logger logger = LoggerFactory.getLogger(CheckinEmotionServiceImpl.class);
    @Resource
    private final CheckinEmotionRepository checkinEmotionRepository;

    public CheckinEmotionServiceImpl(CheckinEmotionRepository checkinEmotionRepository) {
        this.checkinEmotionRepository = checkinEmotionRepository;
    }

    @Override
    public List<CheckinEmotion> findByCheckinDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        List<CheckinEmotion> results = checkinEmotionRepository.findByCheckinTime(startOfDay);
        logger.info("按日期查询情绪检查记录 - 日期：{}，找到记录数：{}", date, results.size());
        return results;
    }

    @Override
    public Page<CheckinEmotion> findByCheckinDates(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        LocalDateTime startOfStartDate = startDate.atStartOfDay();
        LocalDateTime startOfEndDate = endDate.plusDays(1).atStartOfDay();
        Page<CheckinEmotion> results = checkinEmotionRepository.findByCheckinTimeBetween(startOfStartDate, startOfEndDate, pageable);
        logger.info("按日期范围查询情绪检查记录 - 起始日期：{}，结束日期：{}，找到的页面记录数：{}", startDate, endDate, results.getNumberOfElements());
        return results;
    }

    @Override
    public List<CheckinEmotion> findByUserId(int userId) {
        List<CheckinEmotion> results = checkinEmotionRepository.findByUserId(userId);
        logger.info("按用户ID查询情绪检查记录 - 用户ID：{}，找到记录数：{}", userId, results.size());
        return results;
    }

    @Override
    public Page<CheckinEmotion> findAll(Pageable pageable) {
        Page<CheckinEmotion> results = checkinEmotionRepository.findAll(pageable);
        logger.info("查询所有情绪检查记录 - 找到的总记录数：{}", results.getTotalElements());
        return results;
    }

    @Override
    public Optional<CheckinEmotion> findById(String id) {
        Optional<CheckinEmotion> result = checkinEmotionRepository.findById(id);
        result.ifPresentOrElse(
                r -> logger.info("按ID查询情绪检查记录 - ID：{}，找到记录", id),
                () -> logger.info("按ID查询情绪检查记录 - ID：{}，未找到记录", id));
        return result;
    }

    @Override
    public CheckinEmotion save(CheckinEmotion checkinEmotion) {
        validateCheckinEmotion(checkinEmotion);
        CheckinEmotion savedCheckinEmotion = checkinEmotionRepository.save(checkinEmotion);
        logger.info("保存情绪检查记录 - 保存成功，用户ID：{}", savedCheckinEmotion.getUserId());
        return savedCheckinEmotion;
    }

    @Override
    public void deleteById(String id) {
        checkinEmotionRepository.deleteById(id);
        logger.info("删除情绪检查记录 - ID：{}，已删除", id);
    }

    private void validateCheckinEmotion(CheckinEmotion checkinEmotion) {
        double totalPercentage = checkinEmotion.getEmotions().values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();
        if (totalPercentage != 100.0) {
            logger.error("验证情绪检查记录 - 情绪百分比总和不为100%，实际为：{}", totalPercentage);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "所有情绪的百分比总和必须等于100%");
        }
    }
}
