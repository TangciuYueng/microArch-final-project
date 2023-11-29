package cn.edu.tongji.Emotions.controller;

import cn.edu.tongji.Emotions.model.MusicReference;
import cn.edu.tongji.Emotions.service.MusicReferenceService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/musicReferences")
public class MusicReferenceController {

    @Resource
    private final MusicReferenceService musicReferenceService;

    @Autowired
    public MusicReferenceController(MusicReferenceService musicReferenceService) {
        this.musicReferenceService = musicReferenceService;
    }

    @GetMapping
    public ResponseEntity<Page<?>> getAllMusicReferences(Pageable pageable) {
        return ResponseEntity.ok(musicReferenceService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMusicReferenceById(@PathVariable String id) {
        return musicReferenceService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createMusicReference(@RequestBody MusicReference musicReference) {
        return ResponseEntity.ok(musicReferenceService.save(musicReference));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMusicReference(@PathVariable String id, @RequestBody MusicReference musicReference) {
        if (!musicReferenceService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        musicReference.setId(id);
        return ResponseEntity.ok(musicReferenceService.save(musicReference));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMusicReference(@PathVariable String id) {
        if (!musicReferenceService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        musicReferenceService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // Additional endpoints can be added here
}
