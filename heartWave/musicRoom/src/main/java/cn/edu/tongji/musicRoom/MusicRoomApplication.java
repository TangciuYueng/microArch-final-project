package cn.edu.tongji.musicRoom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MusicRoomApplication {
    public static void main(String[] args) {
        SpringApplication.run(MusicRoomApplication.class, args);
    }
}
