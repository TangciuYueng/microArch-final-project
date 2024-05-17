package cn.edu.tongji.friend.job;

import cn.edu.tongji.friend.model.ChatRecord;
import cn.edu.tongji.friend.model.RedisChatRecord;
import cn.edu.tongji.friend.repository.ChatRecordRepository;
import jakarta.annotation.Resource;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static cn.edu.tongji.friend.repository.RedisChatRecordRepository.*;

public class RedisToMysqlJob extends QuartzJobBean {
    @Resource
    private ChatRecordRepository chatRecordRepository;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private DateTimeFormatter timeFormatter;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Executing redis to mysql...");

        long successCount = 0;  //成功插入记录数
        Object totalObject = redisTemplate.opsForValue().get(CHAT_COUNT_KEY);      //聊天记录总数
        Object jobObject = redisTemplate.opsForValue().get(CHAT_CACHE_COUNT_KEY);  //缓存中的记录总数
        long totalSize = totalObject == null ? 0 : Long.parseLong(totalObject.toString());
        long jobSize = jobObject == null ? 0 : Long.parseLong(jobObject.toString());

        //逐条读取聊天记录，存入MySQL数据库中（id从1开始）
        for (long i = totalSize - jobSize + 1; i <= totalSize; i++) {
            RedisChatRecord record = (RedisChatRecord) redisTemplate.opsForHash().get(CHAT_HASH_KEY, String.valueOf(i));

            if (record != null) {
                chatRecordRepository.save(ChatRecord.builder()
                        .senderId(record.getSenderId())
                        .receiverId(record.getReceiverId())
                        .sendTime(LocalDateTime.parse(record.getSendTime(), timeFormatter))
                        .content(record.getContent())
                        .type(record.getType())
                        .isDeleted(false)
                        .build()
                );

                successCount++;
            }
        }

        System.out.println("Total: " + jobSize + ", success: " + successCount);

        //清除Hash：id -> 聊天记录
        redisTemplate.delete(CHAT_HASH_KEY);
        System.out.println("Chat hash key clear");

        //清除ZSet：聊天双方id -> 按时间顺序排列的聊天记录id列表
        Set<String> keys = redisTemplate.keys(CHAT_SORTED_SET_KEY + '*');
        if (keys != null) {
            for (String key : keys) {
                redisTemplate.delete(key);
            }
        }
        System.out.println("Chat sorted set key clear");

        //设置Value：缓存中的聊天记录数为0
        redisTemplate.opsForValue().set(CHAT_CACHE_COUNT_KEY, 0);
        System.out.println("Chat cache count key reset");
    }
}
