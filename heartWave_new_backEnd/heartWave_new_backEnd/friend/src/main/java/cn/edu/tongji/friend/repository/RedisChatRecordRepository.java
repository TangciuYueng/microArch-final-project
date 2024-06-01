package cn.edu.tongji.friend.repository;

import cn.edu.tongji.friend.model.ChatRecordType;
import cn.edu.tongji.friend.model.RedisChatRecord;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RedisChatRecordRepository {
    public static final String CHAT_COUNT_KEY = "chat_count";
    public static final String CHAT_CACHE_COUNT_KEY = "chat_cache_count";
    public static final String CHAT_HASH_KEY = "chat_records";
    public static final String CHAT_SORTED_SET_KEY = "chat_sorted_set";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private DateTimeFormatter timeFormatter;

    private Long generateId() {
        redisTemplate.opsForValue().increment(CHAT_CACHE_COUNT_KEY);   //缓存聊天数自增
        return redisTemplate.opsForValue().increment(CHAT_COUNT_KEY);  //总聊天数自增，并返回作为新聊天记录的id
    }

    public void save(RedisChatRecord chatRecord) {
        //生成新id，并记录该条聊天记录
        Long newId = generateId();
        chatRecord.setId(newId);

        if (chatRecord.getType() == ChatRecordType.PIC) {
            chatRecord.setContent("chatRecord/single/" + newId + ".jpg");
        }

        redisTemplate.opsForHash().put(CHAT_HASH_KEY, newId.toString(), chatRecord);

        //将该条聊天记录发送的时间作为score，加入以双方id为键、对应聊天记录集合为值的键值对，以便后续获取时已经按照时间排序
        final long sid = chatRecord.getSenderId();
        final long rid = chatRecord.getReceiverId();
        String sortedSetKey = sid < rid ? (sid + ":" + rid) : (rid + ":" + sid);
        LocalDateTime sendTime = LocalDateTime.parse(chatRecord.getSendTime(), timeFormatter);
        redisTemplate.opsForZSet().add(CHAT_SORTED_SET_KEY + ':' + sortedSetKey, newId.toString(), sendTime.toInstant(ZoneOffset.UTC).toEpochMilli());
    }

    public List<RedisChatRecord> get(long uid1, long uid2) {
        String sortedSetKey = uid1 < uid2 ? (uid1 + ":" + uid2) : (uid2 + ":" + uid1);
        Set<Object> chatIds = redisTemplate.opsForZSet().range(CHAT_SORTED_SET_KEY + ":" + sortedSetKey, 0, -1);

        // 根据聊天记录 ID 从 Hash 中获取聊天记录
        if (chatIds != null) {
            List<Object> chatRecords = redisTemplate.opsForHash().multiGet(CHAT_HASH_KEY, chatIds);

            // 处理获取到的聊天记录
            return chatRecords.stream()
                    .filter(Objects::nonNull)
                    .map(RedisChatRecord.class::cast)
                    .collect(Collectors.toList());
        }

        else
            return new ArrayList<>();
    }
}
