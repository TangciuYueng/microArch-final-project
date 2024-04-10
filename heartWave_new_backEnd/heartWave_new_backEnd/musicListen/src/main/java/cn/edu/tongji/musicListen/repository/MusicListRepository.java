package cn.edu.tongji.musicListen.repository;

import cn.edu.tongji.musicListen.domain.MusicList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MusicListRepository extends JpaRepository<MusicList, Integer>, JpaSpecificationExecutor<MusicList> {
    Page<MusicList> findByIsDeletedFalseAndIsOpenedFalseAndType(PageRequest pageRequest, String type);
    Page<MusicList> findByIsDeletedFalseAndNameContaining(PageRequest pageRequest, String name);
}