package cn.edu.tongji.musicListen.repository;

import cn.edu.tongji.musicListen.domain.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends JpaRepository<Music, Integer>, JpaSpecificationExecutor<Music> {
    Page<Music> findByTitleContaining(PageRequest pageRequest, String title);
}
