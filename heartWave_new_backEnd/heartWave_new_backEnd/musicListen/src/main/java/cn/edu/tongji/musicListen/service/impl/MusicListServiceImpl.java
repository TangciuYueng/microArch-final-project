package cn.edu.tongji.musicListen.service.impl;

import cn.edu.tongji.musicListen.domain.MusicList;
import cn.edu.tongji.musicListen.domain.MusicListUserRelationship;
import cn.edu.tongji.musicListen.dto.MusicListMedium;
import cn.edu.tongji.musicListen.dto.MusicListSimple;
import cn.edu.tongji.musicListen.repository.MusicListRepository;
import cn.edu.tongji.musicListen.repository.MusicListUserRelationshipRepository;
import cn.edu.tongji.musicListen.service.MusicListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class MusicListServiceImpl implements MusicListService {
    @Autowired
    MusicListRepository musicListRepository;
    @Autowired
    MusicListUserRelationshipRepository musicListUserRelationshipRepository;

    @Override
    public Optional<MusicList> getMusicListById(Integer id) {
        return musicListRepository.findById(id);
    }

    @Override
    public Page<MusicListSimple> findMusicListSimple(PageRequest pageRequest, String type, Integer userId) {
        if (userId == -1) {
            return musicListRepository.findByIsDeletedFalseAndIsOpenedFalseAndType(pageRequest, type).map(this::converToMusicListSimple);
        }
        return musicListUserRelationshipRepository.findMusicListSimplesByTypeAndUserId(pageRequest, type, userId);
    }

    @Override
    public void addMusicListToUser(Integer musicListId, Integer userId, String type) {
        MusicList musicList = musicListRepository.findById(musicListId).orElseThrow(() -> new IllegalArgumentException("MusicList not found"));

        MusicListUserRelationship relationship = musicListUserRelationshipRepository.findByUserIdAndType(userId, type).orElse(null);

        // 不存在 user 到 musicList 的映射
        if (relationship == null || !Objects.equals(relationship.getMusicList().getId(), musicListId)) {
            relationship = MusicListUserRelationship.builder()
                    .userId(userId)
                    .type(type)
                    .musicList(musicList)
                    .build();

            musicListUserRelationshipRepository.save(relationship);
            musicList.getMusicListUserRelationshipList().add(relationship);
            musicListRepository.save(musicList);
        }
    }

    @Override
    public Page<Integer> getUserWhoTypeMusicList(Integer musicListId, String type, PageRequest pageRequest) {
        return musicListUserRelationshipRepository.findUserIdsByTypeAndMusicListId(type, musicListId, pageRequest);
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
