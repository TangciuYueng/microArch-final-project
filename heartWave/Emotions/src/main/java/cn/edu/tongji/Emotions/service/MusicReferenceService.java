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
public interface MusicReferenceService {

    public Page<MusicReference> findAll(Pageable pageable) ;

    public Optional<MusicReference> findById(String id);
    public MusicReference save(MusicReference musicReference);
    public void deleteById(String id);

}
