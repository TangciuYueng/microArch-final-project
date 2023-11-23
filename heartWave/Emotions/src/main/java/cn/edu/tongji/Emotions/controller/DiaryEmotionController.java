package cn.edu.tongji.Emotions.controller;

import cn.edu.tongji.Emotions.model.DiaryEmotion;
import cn.edu.tongji.Emotions.service.DiaryEmotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diaryEmotions")
public class DiaryEmotionController {

    private final DiaryEmotionService diaryEmotionService;

    @Autowired
    public DiaryEmotionController(DiaryEmotionService diaryEmotionService) {
        this.diaryEmotionService = diaryEmotionService;
    }

    @GetMapping
    public ResponseEntity<List<DiaryEmotion>> getAllDiaryEmotions() {
        return ResponseEntity.ok(diaryEmotionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiaryEmotion> getDiaryEmotionById(@PathVariable String id) {
        return diaryEmotionService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DiaryEmotion> createDiaryEmotion(@RequestBody DiaryEmotion diaryEmotion) {
        return ResponseEntity.ok(diaryEmotionService.save(diaryEmotion));
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