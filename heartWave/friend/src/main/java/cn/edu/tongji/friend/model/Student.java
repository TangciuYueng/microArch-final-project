package cn.edu.tongji.friend.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("student")
@Data
@Builder
public class Student {
    @Id
    private String id;
    private String name;
    private Integer age;
}
