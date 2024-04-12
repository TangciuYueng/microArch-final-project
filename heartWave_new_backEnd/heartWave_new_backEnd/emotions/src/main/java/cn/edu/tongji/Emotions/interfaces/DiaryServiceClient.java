package cn.edu.tongji.Emotions.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "diary-writing-service", path = "/api/diary")
public interface DiaryServiceClient {
    @GetMapping("/diaries/{id}")
    ResponseEntity<?> getDiaryById(@PathVariable("id") int id);
}
