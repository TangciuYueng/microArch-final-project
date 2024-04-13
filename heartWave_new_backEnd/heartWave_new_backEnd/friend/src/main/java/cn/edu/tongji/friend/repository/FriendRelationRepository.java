package cn.edu.tongji.friend.repository;

import cn.edu.tongji.friend.model.FriendRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FriendRelationRepository extends JpaRepository<FriendRelation, Long>, JpaSpecificationExecutor<FriendRelation> {
    FriendRelation findFirstByUserIdAndFriendId(Long userId, Long friendId);
    List<FriendRelation> findByUserId(Long userId);
}
