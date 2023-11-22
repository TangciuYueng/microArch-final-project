package cn.edu.tongji.Emotions.controller;

import cn.edu.tongji.Emotions.model.MusicEmotion;
import cn.edu.tongji.Emotions.service.MusicEmotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/musicEmotions")
public class MusicEmotionController {

    private final MusicEmotionService musicEmotionService;

    @Autowired
    public MusicEmotionController(MusicEmotionService musicEmotionService) {
        this.musicEmotionService = musicEmotionService;
    }

    @GetMapping
    public ResponseEntity<List<MusicEmotion>> getAllMusicEmotions() {
        return ResponseEntity.ok(musicEmotionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicEmotion> getMusicEmotionById(@PathVariable String id) {
        return musicEmotionService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MusicEmotion> createMusicEmotion(@RequestBody MusicEmotion musicEmotion) {
        return ResponseEntity.ok(musicEmotionService.save(musicEmotion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusicEmotion> updateMusicEmotion(@PathVariable String id, @RequestBody MusicEmotion musicEmotion) {
        if (!musicEmotionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        musicEmotion.setId(id);
        return ResponseEntity.ok(musicEmotionService.save(musicEmotion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMusicEmotion(@PathVariable String id) {
        if (!musicEmotionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        musicEmotionService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // Additional endpoints can be added here
}
