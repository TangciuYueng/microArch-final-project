package cn.edu.tongji.Emotions.controller;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> getDiaryEmotionById(@PathVariable String id) {
        return diaryEmotionService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createDiaryEmotion(@RequestBody DiaryEmotion diaryEmotion) {
        try {
            // 首先，验证 userId 是否存在
            ResponseEntity<?> userResponse = loginServiceClient.getUserById(diaryEmotion.getUserId());
            if (userResponse.getStatusCode() != HttpStatus.OK) {
                // 如果用户不存在
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + diaryEmotion.getUserId() + " not found");
            }

            // 接着，验证 diaryId 是否存在
            int diaryId = diaryEmotion.getDiaryId();
            ResponseEntity<?> diaryResponse = diaryServiceClient.getDiaryById(diaryId);
            if (diaryResponse.getStatusCode() != HttpStatus.OK) {
                // 如果日记不存在
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Diary with ID " + diaryId + " not found");
            }

            // 如果 userId 和 diaryId 都存在，则保存并返回 DiaryEmotion
            return ResponseEntity.ok(diaryEmotionService.save(diaryEmotion));
        } catch (NumberFormatException e) {
            // 捕获格式错误异常
            return ResponseEntity.badRequest().body("Invalid ID format");
        } catch (Exception e) {
            // 捕获并处理异常
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during service call: " + e.getMessage());
        }
    }




    @PutMapping("/{id}")
    public ResponseEntity<DiaryEmotion> updateDiaryEmotion(@PathVariable String id, @RequestBody DiaryEmotion diaryEmotion) {
        if (!diaryEmotionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        diaryEmotion.setId(id);
        return ResponseEntity.ok(diaryEmotionService.save(diaryEmotion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiaryEmotion(@PathVariable String id) {
        if (!diaryEmotionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        diaryEmotionService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
