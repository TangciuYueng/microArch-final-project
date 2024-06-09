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

    /**
     * 根据音乐列表ID获取音乐列表详细信息
     *
     * @param id 音乐列表ID
     * @return 包含音乐列表信息的Optional对象
     */
    @Override
    public Optional<MusicList> getMusicListById(Integer id) {
        return musicListRepository.findById(id);
    }

    /**
     * 根据类型和用户ID分页查询简化的音乐列表信息
     *
     * @param pageRequest 分页请求对象
     * @param type        音乐列表类型
     * @param userId      用户ID
     * @return 包含简化音乐列表信息的Page对象
     */
    @Override
    public Page<MusicListSimple> findMusicListSimple(PageRequest pageRequest, String type, Integer userId) {
        if (userId == -1) {
            return musicListRepository.findByIsDeletedFalseAndIsOpenedFalseAndType(pageRequest, type).map(this::converToMusicListSimple);
        }
        return musicListUserRelationshipRepository.findMusicListSimplesByTypeAndUserId(pageRequest, type, userId);
    }

    /**
     * 将音乐列表添加到用户 联系集
     *
     * @param musicListId 音乐列表ID
     * @param userId      用户ID
     * @param type        用户类型
     */
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

    /**
     * 获取喜欢音乐列表的用户ID列表
     *
     * @param musicListId 音乐列表ID
     * @param type        用户类型
     * @param pageRequest 分页请求对象
     * @return 包含用户信息的Page对象
     */
    @Override
    public Page<Integer> getUserWhoTypeMusicList(Integer musicListId, String type, PageRequest pageRequest) {
        return musicListUserRelationshipRepository.findUserIdsByTypeAndMusicListId(type, musicListId, pageRequest);
    }

    /**
     * 根据 type 获取给用户拥有的 MusicList 数量
     *
     * @param userId 用户ID
     * @param type   MusicList 类型
     * @return 用户拥有的 MusicList 数量
     */
    @Override
    public Integer getTypeMusicListCount(Integer userId, String type) {
        return musicListUserRelationshipRepository.getTypeMusicListCount(userId, type);
    }

    /**
     * 根据名称分页模糊查询音乐列表信息
     *
     * @param pageRequest 分页请求对象
     * @param name        音乐列表名称
     * @return 包含音乐列表信息的Page对象
     */
    @Override
    public Page<MusicListMedium> findMusicListByName(PageRequest pageRequest, String name) {
        return musicListRepository.findByIsDeletedFalseAndNameContaining(pageRequest, name).map(this::converToMusicListMedium);
    }

    /**
     * 将MusicList对象转换为MusicListMedium对象
     *
     * @param musicList 待转换的MusicList对象
     * @return 转换后的MusicListMedium对象
     */
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

    /**
     * 将MusicList对象转换为MusicListSimple对象
     *
     * @param musicList 待转换的MusicList对象
     * @return 转换后的MusicListSimple对象
     */
    private MusicListSimple converToMusicListSimple(MusicList musicList) {
        return MusicListSimple.builder()
                .id(musicList.getId())
                .name(musicList.getName())
                .src(musicList.getSrc())
                .build();
    }

    @Override
    public MusicList saveMusicList(MusicList musicList) {
        // Save the music list to the repository and return the saved entity
        return musicListRepository.save(musicList);
    }
    @Override
    public boolean deleteMusicList(Integer id) {
        // Check if the music list exists and delete it if it does
        if (musicListRepository.existsById(id)) {
            musicListRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
