package cn.edu.tongji.login.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(5000)) // 设置连接超时时间为5秒
                .setReadTimeout(Duration.ofMillis(5000))    // 设置读取超时时间为5秒
                .build();
    }
}
