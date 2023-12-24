package cn.edu.tongji.friend.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "login-service", path = "/api/user")
public interface LoginServiceClient {
    @GetMapping("/{id}")
    ResponseEntity<?> getUserById(@PathVariable("id") int id);
}
