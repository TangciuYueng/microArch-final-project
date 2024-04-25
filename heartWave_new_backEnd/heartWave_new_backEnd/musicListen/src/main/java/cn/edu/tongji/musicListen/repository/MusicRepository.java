package cn.edu.tongji.musicListen.repository;

import cn.edu.tongji.musicListen.domain.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * 根据音乐ID列表查询对应的音乐信息并进行分页返回
     *
     * @param musicIds 包含音乐ID的列表
     * @param pageRequest 分页请求对象
     * @return 包含音乐信息的分页对象
     */
    @Query("SELECT m FROM Music m WHERE m.id IN :musicIds")
    Page<Music> findMusicByIds(@Param("musicIds") List<Integer> musicIds, PageRequest pageRequest);
}
