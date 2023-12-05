package cn.edu.tongji.EmotionProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class EmotionProcessorApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmotionProcessorApplication.class, args);
    }
}
