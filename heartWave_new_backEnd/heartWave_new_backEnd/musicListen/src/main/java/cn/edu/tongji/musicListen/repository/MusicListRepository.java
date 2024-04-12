package cn.edu.tongji.musicListen.repository;

import cn.edu.tongji.musicListen.domain.MusicList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MusicListRepository extends JpaRepository<MusicList, Integer>, JpaSpecificationExecutor<MusicList> {
    /**
     * 根据未删除、未公开和类型查询音乐列表，返回分页结果
     *
     * @param pageRequest 分页请求对象
     * @param type        音乐列表类型
     * @return 包含查询结果的分页对象
     */
    Page<MusicList> findByIsDeletedFalseAndIsOpenedFalseAndType(PageRequest pageRequest, String type);

    /**
     * 根据未删除和名称包含关键词查询音乐列表，返回分页结果
     *
     * @param pageRequest 分页请求对象
     * @param name        音乐列表名称关键词
     * @return 包含查询结果的分页对象
     */
    Page<MusicList> findByIsDeletedFalseAndNameContaining(PageRequest pageRequest, String name);
}