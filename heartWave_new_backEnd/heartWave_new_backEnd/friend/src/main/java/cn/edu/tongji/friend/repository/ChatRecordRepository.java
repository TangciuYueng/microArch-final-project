package cn.edu.tongji.friend.repository;

import cn.edu.tongji.friend.model.RedisChatRecord;
import org.springframework.data.repository.CrudRepository;

public interface ChatRecordRepository extends CrudRepository<RedisChatRecord, Long> {
}
