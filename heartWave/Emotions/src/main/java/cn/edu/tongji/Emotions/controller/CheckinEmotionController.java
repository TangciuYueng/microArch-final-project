package cn.edu.tongji.Emotions.controller;

import cn.edu.tongji.Emotions.interfaces.LoginServiceClient;
import cn.edu.tongji.Emotions.model.CheckinEmotion;
import cn.edu.tongji.Emotions.service.CheckinEmotionService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/checkinEmotions")
public class CheckinEmotionController {

//    @Autowired
//    private RestTemplate restTemplate; // 注入负载均衡的 RestTemplate

    @Autowired
    private LoginServiceClient loginServiceClient;

    @Resource
    private final CheckinEmotionService checkinEmotionService;

    @Autowired
    public CheckinEmotionController(CheckinEmotionService checkinEmotionService) {
        this.checkinEmotionService = checkinEmotionService;
    }

//    public String calllogin() {
//        return restTemplate.getForObject("http://login-service/controller/test/", String.class);
//    }
    @GetMapping
    public ResponseEntity<Page<CheckinEmotion>> getAllCheckinEmotions(Pageable pageable) {
        //System.out.println(loginServiceClient.test());
        return ResponseEntity.ok(checkinEmotionService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckinEmotion> getCheckinEmotionById(@PathVariable String id) {
        return checkinEmotionService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/byUser/{id}")
    public ResponseEntity<CheckinEmotion> getCheckinEmotionByUserId(@PathVariable String UserId) {
        return checkinEmotionService.findByUserId(UserId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    // 根据日期获取情绪打卡记录的接口
    @GetMapping("/byDate/{date}")
    public ResponseEntity<List<CheckinEmotion>> getCheckinEmotionByDay(@PathVariable String date) {
        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build(); // 日期格式错误时返回错误请求响应
        }

        List<CheckinEmotion> emotions = checkinEmotionService.findByCheckinDate(parsedDate);
        if (emotions.isEmpty()) {
            return ResponseEntity.notFound().build(); // 如果当天没有记录，则返回未找到的响应
        }

        return ResponseEntity.ok(emotions); // 返回找到的记录
    }

    @GetMapping("/byDates/{date}")
    public ResponseEntity<Page<CheckinEmotion>> getCheckinEmotionByDays(@PathVariable String date, Pageable pageable) {
        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        }

        LocalDate startDate = parsedDate.minusWeeks(1);
        Page<CheckinEmotion> emotions = checkinEmotionService.findByCheckinDates(startDate, parsedDate, pageable);
        if (emotions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(emotions);
    }

    @PostMapping
    public ResponseEntity<?> createCheckinEmotion(@RequestBody CheckinEmotion checkinEmotion) {
        try {
            // 调用 LoginService 来验证 userId 是否存在
            ResponseEntity<?> userResponse = loginServiceClient.getUserById(Integer.parseInt(checkinEmotion.getUserId()));

            // 检查用户是否存在
            if (userResponse.getStatusCode() == HttpStatus.OK) {
                // 如果用户存在，保存并返回 CheckinEmotion
                return ResponseEntity.ok(checkinEmotionService.save(checkinEmotion));
            } else {
                // 如果用户不存在（任何非200的HTTP状态码），返回适当的错误响应
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + checkinEmotion.getUserId() + " not found");
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user ID format");
        } catch (Exception e) {
            // 处理其他可能的异常，如网络问题或服务不可用
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while verifying user ID");
        }
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