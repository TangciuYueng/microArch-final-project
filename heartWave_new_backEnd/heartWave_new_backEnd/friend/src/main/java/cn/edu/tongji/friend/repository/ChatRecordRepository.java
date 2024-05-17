package cn.edu.tongji.friend.repository;

import cn.edu.tongji.friend.model.ChatRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChatRecordRepository extends JpaRepository<ChatRecord, Long>, JpaSpecificationExecutor<ChatRecord> {
}
