package cn.edu.tongji.Emotions.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "login-service", path = "")
public interface LoginServiceClient {

    @GetMapping("/test")
    String test();
}