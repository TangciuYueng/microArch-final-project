package cn.edu.tongji.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ChatRoomThreadPoolConfig {

    @Bean
    public ExecutorService getChatRoomThreadPool() {
        return Executors.newCachedThreadPool();
    }
}
