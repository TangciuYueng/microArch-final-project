// EmotionController.java
package cn.edu.tongji.Emotions.controller;

import cn.edu.tongji.Emotions.interfaces.LoginServiceClient;
import cn.edu.tongji.Emotions.model.Emotion;
import cn.edu.tongji.Emotions.service.EmotionService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emotions")
public class EmotionController {



    @Autowired
    private LoginServiceClient loginServiceClient;

    @Resource
    private final EmotionService emotionService;

    @Autowired
    public EmotionController(EmotionService emotionService) {
        this.emotionService = emotionService;
    }

    @GetMapping
    public ResponseEntity<Page<?>> getAllEmotions(Pageable pageable) {
        return ResponseEntity.ok(emotionService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmotionById(@PathVariable String id) {
        return emotionService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createEmotion(@RequestBody Emotion emotion) {

        try {
            // 调用 LoginService 来验证 userId 是否存在
            ResponseEntity<?> userResponse = loginServiceClient.getUserById(Integer.parseInt(emotion.getUserId()));
            // 检查用户是否存在
            if (userResponse.getStatusCode() == HttpStatus.OK) {
                // 如果用户存在，保存并返回 CheckinEmotion
                return ResponseEntity.ok(emotionService.save(emotion));
            } else {
                // 如果用户不存在（任何非200的HTTP状态码），返回适当的错误响应
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + emotion.getUserId() + " not found");
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user ID format");
        } catch (Exception e) {
            // 处理其他可能的异常，如网络问题或服务不可用
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while verifying user ID");
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmotion(@PathVariable String id, @RequestBody Emotion emotion) {
        if (!emotionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        emotion.setId(id);
        return ResponseEntity.ok(emotionService.save(emotion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmotion(@PathVariable String id) {
        if (!emotionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        emotionService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // Additional endpoints can be added here
}
