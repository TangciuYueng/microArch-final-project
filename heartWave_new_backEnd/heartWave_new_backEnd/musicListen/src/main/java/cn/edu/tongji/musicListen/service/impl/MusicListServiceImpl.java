package cn.edu.tongji.musicListen.service.impl;

import cn.edu.tongji.musicListen.domain.Music;
import cn.edu.tongji.musicListen.domain.MusicList;
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
    public Page<MusicList> findAdminMusicLists(PageRequest pageRequest) {
        return musicListRepository.findAdminMusicLists(pageRequest);
    }
}
