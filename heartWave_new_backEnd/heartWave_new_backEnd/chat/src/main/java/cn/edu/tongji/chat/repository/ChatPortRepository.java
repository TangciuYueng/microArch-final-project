package cn.edu.tongji.chat.repository;

import cn.edu.tongji.chat.model.ChatPort;
import cn.edu.tongji.chat.model.ChatPortType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ChatPortRepository extends CrudRepository<ChatPort, Long> {
    ChatPort findFirstByHostId(Long hostId);
    ChatPort findFirstByHostIdAndTypeAndGroupId(Long hostId, ChatPortType type, Long groupId);

    @Modifying
    @Transactional
    @Query("UPDATE ChatPort SET hostId = :newHostId, type = :newType, groupId = :newGroupId WHERE port = :port")
    void updateHostIdAndTypeAndGroupIdByPort(
            @Param("port") Short port,
            @Param("newHostId") Long newHostId,
            @Param("newType") ChatPortType newType,
            @Param("newGroupId") Long newGroupId);
}
