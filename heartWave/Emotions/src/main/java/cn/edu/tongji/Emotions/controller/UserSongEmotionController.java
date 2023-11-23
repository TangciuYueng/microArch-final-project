// UserSongEmotionController.java
package cn.edu.tongji.Emotions.controller;

import cn.edu.tongji.Emotions.model.UserSongEmotion;
import cn.edu.tongji.Emotions.service.UserSongEmotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userSongEmotions")
public class UserSongEmotionController {

    private final UserSongEmotionService userSongEmotionService;

    @Autowired
    public UserSongEmotionController(UserSongEmotionService userSongEmotionService) {
        this.userSongEmotionService = userSongEmotionService;
    }

    @GetMapping
    public ResponseEntity<List<UserSongEmotion>> getAllUserSongEmotions() {
        return ResponseEntity.ok(userSongEmotionService.findAll());
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