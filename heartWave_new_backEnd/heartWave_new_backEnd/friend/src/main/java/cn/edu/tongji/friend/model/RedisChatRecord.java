package cn.edu.tongji.friend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Builder
@Data
@RedisHash("chat-record")
@AllArgsConstructor
@NoArgsConstructor
public class RedisChatRecord {
    @Id
    private Long id;

    private Long senderId;
    private Long receiverId;
    private String sendTime;
    private ChatRecordType type;
    private String content;
}
