package cn.edu.tongji.musicListen.service;

import cn.edu.tongji.musicListen.common.Result;
import cn.edu.tongji.musicListen.domain.MusicList;
import cn.edu.tongji.musicListen.dto.MusicListMedium;
import cn.edu.tongji.musicListen.dto.MusicListSimple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface MusicListService {
    /**
     * 根据音乐列表ID获取音乐列表详细信息
     *
     * @param id 音乐列表ID
     * @return 包含音乐列表信息的Optional对象
     */
    Optional<MusicList> getMusicListById(Integer id);

    /**
     * 根据类型和用户ID分页查询简化的音乐列表信息
     *
     * @param pageRequest 分页请求对象
     * @param type        音乐列表类型
     * @param userId      用户ID
     * @return 包含简化音乐列表信息的Page对象
     */
    Page<MusicListSimple> findMusicListSimple(PageRequest pageRequest, String type, Integer userId);

    /**
     * 根据名称分页模糊查询音乐列表信息
     *
     * @param pageRequest 分页请求对象
     * @param name        音乐列表名称
     * @return 包含音乐列表信息的Page对象
     */
    Page<MusicListMedium> findMusicListByName(PageRequest pageRequest, String name);

    /**
     * 将音乐列表添加到用户 联系集
     *
     * @param musicListId 音乐列表ID
     * @param userId      用户ID
     * @param type        用户类型
     */
    void addMusicListToUser(Integer musicListId, Integer userId, String type);

    /**
     * 获取喜欢音乐列表的用户ID列表
     *
     * @param musicListId 音乐列表ID
     * @param type        用户类型
     * @param pageRequest 分页请求对象
     * @return 包含用户信息的Page对象
     */
    Page<Integer> getUserWhoTypeMusicList(Integer musicListId, String type, PageRequest pageRequest);
}
