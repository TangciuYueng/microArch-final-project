package cn.edu.tongji.Emotions.controller;

import cn.edu.tongji.Emotions.interfaces.MusicServiceClient;
import cn.edu.tongji.Emotions.model.MusicEmotion;
import cn.edu.tongji.Emotions.service.MusicEmotionService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/musicEmotions")
public class MusicEmotionController {


    @Autowired
    private MusicServiceClient musicServiceClient;

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
        try {
            // 调用 LoginService 来验证 userId 是否存在
            ResponseEntity<?> userResponse = musicServiceClient.getMusicById(Integer.parseInt(musicEmotion.getMusicId()));
            // 检查用户是否存在
            if (userResponse.getStatusCode() == HttpStatus.OK) {
                // 如果用户存在，保存并返回 CheckinEmotion
                return ResponseEntity.ok(musicEmotionService.save(musicEmotion));
            } else {
                // 如果用户不存在（任何非200的HTTP状态码），返回适当的错误响应
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Music with ID " + musicEmotion.getMusicId() + " not found");
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Music ID format");
        } catch (Exception e) {
            // 处理其他可能的异常，如网络问题或服务不可用
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while verifying Music ID");
        }
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
