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
    /**
     * 根据用户类型和用户ID查询简化的音乐列表信息，返回分页结果
     *
     * @param pageRequest 分页请求对象
     * @param type        用户类型
     * @param userId      用户ID
     * @return 包含简化音乐列表信息的分页对象
     */
    @Query("SELECT new cn.edu.tongji.musicListen.dto.MusicListSimple(l.id as id, l.name as name, l.src as src) FROM MusicListUserRelationship r JOIN musicList l WHERE r.type = :type AND r.userId = :userId")
    Page<MusicListSimple> findMusicListSimplesByTypeAndUserId(PageRequest pageRequest, @Param("type") String type, @Param("userId") Integer userId);

    /**
     * 根据用户ID和用户类型查询音乐列表用户关系
     *
     * @param userId 用户ID
     * @param type   用户类型
     * @return 包含音乐列表用户关系的Optional对象
     */
    Optional<MusicListUserRelationship> findByUserIdAndType(Integer userId, String type);

    /**
     * 根据用户类型和音乐列表ID查询用户ID，返回分页结果
     *
     * @param type        用户类型
     * @param musicListId 音乐列表ID
     * @param pageRequest 分页请求对象
     * @return 包含用户ID的分页对象
     */
    @Query("SELECT m.userId FROM MusicListUserRelationship m WHERE m.type = :type AND m.musicList.id = :musicListId")
    Page<Integer> findUserIdsByTypeAndMusicListId(@Param("type") String type, @Param("musicListId") Integer musicListId, PageRequest pageRequest);
}