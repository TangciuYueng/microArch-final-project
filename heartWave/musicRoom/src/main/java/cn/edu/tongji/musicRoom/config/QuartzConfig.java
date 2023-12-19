package cn.edu.tongji.musicRoom.config;

import cn.edu.tongji.musicRoom.quartzServer.RedisToMysqlTask;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail RedisToMysqlQuartz() {
        return JobBuilder
                .newJob(RedisToMysqlTask.class)
                .withIdentity("CallPayQuartzTask")
                .storeDurably()
                .build();
    }
    @Bean
    public Trigger CallPayQuartzTaskTrigger() {
        return TriggerBuilder.newTrigger().forJob(RedisToMysqlQuartz())
                .withIdentity("CallPayQuartzTask")
                .withSchedule(CronScheduleBuilder.cronSchedule("*/30 * * * * ?"))
                .build();
    }
}
