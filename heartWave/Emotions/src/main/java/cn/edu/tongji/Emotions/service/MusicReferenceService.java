package cn.edu.tongji.Emotions.service;

import cn.edu.tongji.Emotions.model.MusicReference;
import cn.edu.tongji.Emotions.repository.MusicReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<MusicReference> findAll() {
        return musicReferenceRepository.findAll();
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
