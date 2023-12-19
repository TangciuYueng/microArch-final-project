package cn.edu.tongji.musicRoom.controller;

import cn.edu.tongji.musicRoom.service.ChatRecordService;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
    @Autowired
    private ChatRecordService chatRecordService;

    @GetMapping(value = "/hello/{name}")
    @SentinelResource(value = "sayHello")
    public String hello(@PathVariable String name) {
        return "Hello" + name;
    }

    @PostMapping("/chat-cord/redis")
    public void testRedis() {
        chatRecordService.testSerializableListRedisTemplate();
    }
}
