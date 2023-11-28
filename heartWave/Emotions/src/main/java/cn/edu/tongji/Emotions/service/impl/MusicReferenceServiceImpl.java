package cn.edu.tongji.Emotions.service.impl;

import cn.edu.tongji.Emotions.model.MusicReference;
import cn.edu.tongji.Emotions.repository.MusicReferenceRepository;
import cn.edu.tongji.Emotions.service.MusicReferenceService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MusicReferenceServiceImpl implements MusicReferenceService {

    @Resource
    private final MusicReferenceRepository musicReferenceRepository;

    @Autowired
    public MusicReferenceServiceImpl(MusicReferenceRepository musicReferenceRepository) {
        this.musicReferenceRepository = musicReferenceRepository;
    }

    @Override
    public Page<MusicReference> findAll(Pageable pageable) {
        return musicReferenceRepository.findAll(pageable);
    }

    @Override
    public Optional<MusicReference> findById(String id) {
        return musicReferenceRepository.findById(id);
    }

    @Override
    public MusicReference save(MusicReference musicReference) {
        return musicReferenceRepository.save(musicReference);
    }

    @Override
    public void deleteById(String id) {
        musicReferenceRepository.deleteById(id);
    }

}
