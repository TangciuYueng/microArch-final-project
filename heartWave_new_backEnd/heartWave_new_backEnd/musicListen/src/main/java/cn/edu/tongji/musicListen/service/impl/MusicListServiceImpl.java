package cn.edu.tongji.musicListen.service.impl;

import cn.edu.tongji.musicListen.domain.MusicList;
import cn.edu.tongji.musicListen.dto.MusicListMedium;
import cn.edu.tongji.musicListen.dto.MusicListSimple;
import cn.edu.tongji.musicListen.repository.MusicListRepository;
import cn.edu.tongji.musicListen.service.MusicListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MusicListServiceImpl implements MusicListService {
    @Autowired
    MusicListRepository musicListRepository;
    @Override
    public Optional<MusicList> getMusicListById(Integer id) {
        return musicListRepository.findById(id);
    }

    @Override
    public Page<MusicListSimple> findMusicListSimple(PageRequest pageRequest, String type) {
        return musicListRepository.findByIsDeletedFalseAndIsOpenedFalseAndType(pageRequest, type).map(this::converToMusicListSimple);
    }

    @Override
    public Page<MusicListMedium> findMusicListByName(PageRequest pageRequest, String name) {
        return musicListRepository.findByIsDeletedFalseAndNameContaining(pageRequest, name).map(this::converToMusicListMedium);
    }

    private MusicListMedium converToMusicListMedium(MusicList musicList) {
        return MusicListMedium.builder()
                .id(musicList.getId())
                .name(musicList.getName())
                .type(musicList.getType())
                .createDate(musicList.getCreateDate())
                .src(musicList.getSrc())
                .playCount(musicList.getPlayCount())
                .build();
    }

    private MusicListSimple converToMusicListSimple(MusicList musicList) {
        return MusicListSimple.builder()
                .id(musicList.getId())
                .name(musicList.getName())
                .src(musicList.getSrc())
                .build();
    }

}
