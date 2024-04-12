package cn.edu.tongji.Emotions.controller;

import cn.edu.tongji.Emotions.dto.DiaryEmotionDTO;
import cn.edu.tongji.Emotions.interfaces.DiaryServiceClient;
import cn.edu.tongji.Emotions.interfaces.LoginServiceClient;
import cn.edu.tongji.Emotions.model.DiaryEmotion;
import cn.edu.tongji.Emotions.service.DiaryEmotionService;
import feign.FeignException;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emotions/diary-emotions")
public class DiaryEmotionController {

    @Autowired
    private DiaryServiceClient diaryServiceClient;
    @Autowired
    private LoginServiceClient loginServiceClient;

    @Resource
    private final DiaryEmotionService diaryEmotionService;

    @Autowired
    public DiaryEmotionController(DiaryEmotionService diaryEmotionService) {
        this.diaryEmotionService = diaryEmotionService;
    }

    @GetMapping
    public ResponseEntity<Page<?>> getAllDiaryEmotions(Pageable pageable) {
        return ResponseEntity.ok(diaryEmotionService.findAll(pageable));
    }

    @GetMapping("/diaries/{diaryId}")
    public ResponseEntity<?> getDiaryEmotionByDiaryId(@PathVariable int diaryId) {
        return diaryEmotionService.findByDiaryId(diaryId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<?>> getDiaryEmotionByUserId(@PathVariable int userId) {
        List<?> diaryEmotions = diaryEmotionService.findByUserId(userId);
        if (diaryEmotions.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(diaryEmotions);
        }
    }

    @PostMapping
    public ResponseEntity<?> createDiaryEmotion(@RequestBody DiaryEmotionDTO diaryEmotionDTO) {
        try {
            // 首先，验证 userId 是否存在
            ResponseEntity<?> userResponse = loginServiceClient.getUserById(diaryEmotionDTO.getUserId());
            if (userResponse.getStatusCode() != HttpStatus.OK) {
                // 如果用户不存在
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + diaryEmotionDTO.getUserId() + " not found");
            }

            // 接着，验证 diaryId 是否存在
            int diaryId = diaryEmotionDTO.getDiaryId();
            ResponseEntity<?> diaryResponse = diaryServiceClient.getDiaryById(diaryId);
            if (diaryResponse.getStatusCode() != HttpStatus.OK) {
                // 如果日记不存在
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Diary with ID " + diaryId + " not found");
            }

            // 如果 userId 和 diaryId 都存在，则保存并返回 DiaryEmotion
            return ResponseEntity.ok(diaryEmotionService.save(diaryEmotionDTO));
        } catch (NumberFormatException e) {
            // 捕获格式错误异常
            return ResponseEntity.badRequest().body("Invalid ID format");
        } catch (Exception e) {
            // 捕获并处理异常
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during service call: " + e.getMessage());
        }
    }




    @PutMapping("/{diaryId}")
    public ResponseEntity<DiaryEmotion> updateDiaryEmotion(@PathVariable int diaryId, @RequestBody DiaryEmotionDTO diaryEmotionDTO) {
        if (!diaryEmotionService.findByDiaryId(diaryId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        diaryEmotionDTO.setDiaryId(diaryId);
        return ResponseEntity.ok(diaryEmotionService.save(diaryEmotionDTO));
    }


    @DeleteMapping("/{diaryId}")
    public ResponseEntity<Void> deleteDiaryEmotion(@PathVariable int diaryId) {
        if (!diaryEmotionService.findByDiaryId(diaryId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        diaryEmotionService.deleteById(diaryId);
        return ResponseEntity.ok().build();
    }

}
