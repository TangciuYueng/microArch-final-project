package cn.edu.tongji.musicListen.repository;

import cn.edu.tongji.musicListen.domain.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends JpaRepository<Music, Integer>, JpaSpecificationExecutor<Music> {
    /**
     * 根据包含指定标题关键词的音乐进行查询，返回分页结果
     *
     * @param pageRequest 分页请求对象
     * @param title       音乐标题关键词
     * @return 包含查询结果的分页对象
     */
    Page<Music> findByTitleContaining(PageRequest pageRequest, String title);
}
