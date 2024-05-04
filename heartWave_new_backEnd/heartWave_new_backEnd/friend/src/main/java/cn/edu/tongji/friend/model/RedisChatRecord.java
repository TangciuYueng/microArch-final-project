package cn.edu.tongji.friend.model;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Builder
@RedisHash("chat-record")
public class RedisChatRecord {
    @Id
    private Long id;

    private Long senderId;
    private Long receiverId;
    private LocalDateTime sendTime;
    private ChatRecordType type;
    private String content;
}
