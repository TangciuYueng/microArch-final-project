package cn.edu.tongji.diaryWriting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DiaryWritingApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiaryWritingApplication.class, args);
    }
}
