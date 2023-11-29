package cn.edu.tongji.Emotions.controller;

import cn.edu.tongji.Emotions.model.MusicEmotion;
import cn.edu.tongji.Emotions.service.MusicEmotionService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/musicEmotions")
public class MusicEmotionController {

    @Resource
    private final MusicEmotionService musicEmotionService;

    @Autowired
    public MusicEmotionController(MusicEmotionService musicEmotionService) {
        this.musicEmotionService = musicEmotionService;
    }

    @GetMapping
    public ResponseEntity<Page<?>> getAllMusicEmotions(Pageable pageable) {
        return ResponseEntity.ok(musicEmotionService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMusicEmotionById(@PathVariable String id) {
        return musicEmotionService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createMusicEmotion(@RequestBody MusicEmotion musicEmotion) {
        return ResponseEntity.ok(musicEmotionService.save(musicEmotion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMusicEmotion(@PathVariable String id, @RequestBody MusicEmotion musicEmotion) {
        if (!musicEmotionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        musicEmotion.setId(id);
        return ResponseEntity.ok(musicEmotionService.save(musicEmotion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMusicEmotion(@PathVariable String id) {
        if (!musicEmotionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        musicEmotionService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // Additional endpoints can be added here
}
