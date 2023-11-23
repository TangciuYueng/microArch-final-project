package cn.edu.tongji.Emotions.controller;

import cn.edu.tongji.Emotions.interfaces.LoginServiceClient;
import cn.edu.tongji.Emotions.model.CheckinEmotion;
import cn.edu.tongji.Emotions.service.CheckinEmotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/checkinEmotions")
public class CheckinEmotionController {

//    @Autowired
//    private RestTemplate restTemplate; // 注入负载均衡的 RestTemplate

    @Autowired
    private LoginServiceClient loginServiceClient;

    private final CheckinEmotionService checkinEmotionService;

    @Autowired
    public CheckinEmotionController(CheckinEmotionService checkinEmotionService) {
        this.checkinEmotionService = checkinEmotionService;
    }

//    public String calllogin() {
//        return restTemplate.getForObject("http://login-service/controller/test/", String.class);
//    }
    @GetMapping
    public ResponseEntity<List<CheckinEmotion>> getAllCheckinEmotions() {
        System.out.println(loginServiceClient.test());
        return ResponseEntity.ok(checkinEmotionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckinEmotion> getCheckinEmotionById(@PathVariable String id) {
        return checkinEmotionService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CheckinEmotion> createCheckinEmotion(@RequestBody CheckinEmotion checkinEmotion) {
        return ResponseEntity.ok(checkinEmotionService.save(checkinEmotion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CheckinEmotion> updateCheckinEmotion(@PathVariable String id, @RequestBody CheckinEmotion checkinEmotion) {
        if (!checkinEmotionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        checkinEmotion.setId(id);
        return ResponseEntity.ok(checkinEmotionService.save(checkinEmotion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCheckinEmotion(@PathVariable String id) {
        if (!checkinEmotionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        checkinEmotionService.deleteById(id);
        return ResponseEntity.ok().build();
    }

// Additional endpoints can be added here
}