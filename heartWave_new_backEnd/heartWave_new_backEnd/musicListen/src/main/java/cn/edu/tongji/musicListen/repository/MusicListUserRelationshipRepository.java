package cn.edu.tongji.musicListen.repository;


import cn.edu.tongji.musicListen.domain.MusicListUserRelationship;
import cn.edu.tongji.musicListen.dto.MusicListSimple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface MusicListUserRelationshipRepository extends JpaRepository<MusicListUserRelationship, Integer>, JpaSpecificationExecutor<MusicListUserRelationship> {
    @Query("SELECT new cn.edu.tongji.musicListen.dto.MusicListSimple(l.id as id, l.name as name, l.src as src) FROM MusicListUserRelationship r JOIN musicList l WHERE r.type = :type AND r.userId = :userId")
    Page<MusicListSimple> findMusicListSimplesByTypeAndUserId(PageRequest pageRequest, @Param("type") String type, @Param("userId") Integer userId);

    Optional<MusicListUserRelationship> findByUserIdAndType(Integer userId, String type);
}