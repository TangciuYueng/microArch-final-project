// EmotionController.java
package cn.edu.tongji.Emotions.controller;

import cn.edu.tongji.Emotions.model.Emotion;
import cn.edu.tongji.Emotions.service.EmotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emotions")
public class EmotionController {

    private final EmotionService emotionService;

    @Autowired
    public EmotionController(EmotionService emotionService) {
        this.emotionService = emotionService;
    }

    @GetMapping
    public ResponseEntity<Page<Emotion>> getAllEmotions(Pageable pageable) {
        return ResponseEntity.ok(emotionService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emotion> getEmotionById(@PathVariable String id) {
        return emotionService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Emotion> createEmotion(@RequestBody Emotion emotion) {
        return ResponseEntity.ok(emotionService.save(emotion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Emotion> updateEmotion(@PathVariable String id, @RequestBody Emotion emotion) {
        if (!emotionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        emotion.setId(id);
        return ResponseEntity.ok(emotionService.save(emotion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmotion(@PathVariable String id) {
        if (!emotionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        emotionService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // Additional endpoints can be added here
}
