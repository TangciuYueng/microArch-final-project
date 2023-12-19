package cn.edu.tongji.musicRoom.repository;

import cn.edu.tongji.musicRoom.model.ChatRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface ChatRecordRepository extends CrudRepository<ChatRecord, Integer> {
}
