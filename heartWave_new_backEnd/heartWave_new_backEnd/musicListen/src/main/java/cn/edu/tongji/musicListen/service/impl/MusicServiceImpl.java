package cn.edu.tongji.musicListen.service.impl;

import cn.edu.tongji.musicListen.domain.Music;
import cn.edu.tongji.musicListen.repository.MusicRepository;
import cn.edu.tongji.musicListen.service.MusicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MusicServiceImpl implements MusicService {
    @Autowired
    private MusicRepository musicRepository;

    @Override
    public Optional<Music> getMusicById(Integer id) {
        return musicRepository.findById(id);
    }

    @Override
    public Page<Music> findAllByPage(PageRequest pageRequest) {
        return musicRepository.findAll(pageRequest);
    }

    @Override
    public Page<Music> findMusicByTitle(PageRequest pageRequest, String title) {
        return musicRepository.findByTitleContaining(pageRequest, title);
    }
}
