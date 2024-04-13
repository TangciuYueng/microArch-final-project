package cn.edu.tongji.friend.repository;

import cn.edu.tongji.friend.model.Friend;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface FriendRepository extends JpaRepository<Friend, Long>, JpaSpecificationExecutor<Friend> {
    Friend findFirstById(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Friend SET chatTime = :newTime WHERE id = :id")
    void updateTimeById(@Param("id") Long id, @Param("newTime") LocalDateTime newTime);
}
