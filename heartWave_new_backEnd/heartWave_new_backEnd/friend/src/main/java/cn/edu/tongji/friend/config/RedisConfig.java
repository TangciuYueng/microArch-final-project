package cn.edu.tongji.friend.config;

import cn.edu.tongji.friend.job.RedisToMysqlJob;
import lombok.Data;
import org.quartz.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.format.DateTimeFormatter;

@Configuration
@ConfigurationProperties(prefix = "spring.data.redis.quartz", ignoreUnknownFields = false)
@Data
public class RedisConfig {
    private String identity;
    private String schedule;

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        // 使用GenericJackson2JsonRedisSerializer替换默认序列化
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();

        // 设置 Key 和 Value 的序列化规则
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        // 初始化 RedisTemplate 序列化完成
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


//    @Bean
//    public JobDetail RedisToMysqlDetail() {
//        return JobBuilder
//                .newJob(RedisToMysqlJob.class)
//                .withIdentity(identity)
//                .storeDurably()
//                .build();
//    }
//
//    @Bean
//    public Trigger CallRedisToMysqlTrigger() {
//        return TriggerBuilder.newTrigger()
//                .forJob(RedisToMysqlDetail())
//                .withIdentity(identity)
//                //.withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 * * ?"))
//                .withSchedule(CronScheduleBuilder.cronSchedule("*/10 * * * * ?"))
//                .build();
//    }
//

    @Bean
    public DateTimeFormatter TimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
}
