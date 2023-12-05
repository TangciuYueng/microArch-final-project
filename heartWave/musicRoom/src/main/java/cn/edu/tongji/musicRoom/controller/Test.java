package cn.edu.tongji.musicRoom.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
    @GetMapping(value = "/hello/{name}")
    @SentinelResource(value = "sayHello")
    public String hello(@PathVariable String name) {
        return "Hello" + name;
    }
}
