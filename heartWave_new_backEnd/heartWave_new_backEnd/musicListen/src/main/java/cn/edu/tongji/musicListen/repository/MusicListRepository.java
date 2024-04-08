package cn.edu.tongji.musicListen.repository;

import cn.edu.tongji.musicListen.domain.MusicList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface MusicListRepository extends JpaRepository<MusicList, Integer>, JpaSpecificationExecutor<MusicList> {
    @Query("SELECT new cn.edu.tongji.musicListen.domain.MusicList(ml.id, ml.name, ml.src) " +
            "FROM MusicList ml WHERE ml.type = 'admin'")
    Page<MusicList> findAdminMusicLists(PageRequest pageRequest);
}