package cn.edu.tongji.Emotions.service;

import cn.edu.tongji.Emotions.model.MusicReference;
import cn.edu.tongji.Emotions.repository.MusicReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MusicReferenceService {

    private final MusicReferenceRepository musicReferenceRepository;

    @Autowired
    public MusicReferenceService(MusicReferenceRepository musicReferenceRepository) {
        this.musicReferenceRepository = musicReferenceRepository;
    }

    public Page<MusicReference> findAll(Pageable pageable) {
        return musicReferenceRepository.findAll(pageable);
    }

    public Optional<MusicReference> findById(String id) {
        return musicReferenceRepository.findById(id);
    }

    public MusicReference save(MusicReference musicReference) {
        return musicReferenceRepository.save(musicReference);
    }

    public void deleteById(String id) {
        musicReferenceRepository.deleteById(id);
    }

}
