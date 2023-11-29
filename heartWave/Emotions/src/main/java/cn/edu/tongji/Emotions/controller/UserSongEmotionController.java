// UserSongEmotionController.java
package cn.edu.tongji.Emotions.controller;

import cn.edu.tongji.Emotions.model.UserSongEmotion;
import cn.edu.tongji.Emotions.service.UserSongEmotionService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/UserSongEmotions")
public class UserSongEmotionController {

    @Resource
    private final UserSongEmotionService userSongEmotionService;

    @Autowired
    public UserSongEmotionController(UserSongEmotionService userSongEmotionService) {
        this.userSongEmotionService = userSongEmotionService;
    }

    @GetMapping
    public ResponseEntity<Page<UserSongEmotion>> getAllUserSongEmotions(Pageable pageable) {
        return ResponseEntity.ok(userSongEmotionService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSongEmotion> getUserSongEmotionById(@PathVariable String id) {
        return userSongEmotionService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserSongEmotion> createUserSongEmotion(@RequestBody UserSongEmotion userSongEmotion) {
        return ResponseEntity.ok(userSongEmotionService.save(userSongEmotion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserSongEmotion> updateUserSongEmotion(@PathVariable String id, @RequestBody UserSongEmotion userSongEmotion) {
        if (!userSongEmotionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        userSongEmotion.setId(id);
        return ResponseEntity.ok(userSongEmotionService.save(userSongEmotion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserSongEmotion(@PathVariable String id) {
        if (!userSongEmotionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        userSongEmotionService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // Additional endpoints can be added here
}