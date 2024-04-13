package cn.edu.tongji.musicListen.service;

import cn.edu.tongji.musicListen.domain.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface MusicService {

    /**
     * 根据音乐ID获取音乐信息
     *
     * @param id 音乐ID
     * @return 包含音乐信息的Optional对象
     */
    Optional<Music> getMusicById(Integer id);

    /**
     * 分页获取所有音乐信息
     *
     * @param pageRequest 分页请求对象
     * @return 包含音乐分页信息的Page对象
     */
    Page<Music> findAllByPage(PageRequest pageRequest);

    /**
     * 根据标题关键词分页搜索音乐信息
     *
     * @param pageRequest 分页请求对象
     * @param title       音乐标题关键词
     * @return 包含搜索结果的Page对象
     */
    Page<Music> findMusicByTitle(PageRequest pageRequest, String title);

    /**
     * 根据用户ID和音乐类型获取用户收藏的对应类型音乐列表并进行分页返回
     *
     * @param userId      用户ID
     * @param type        音乐类型
     * @param pageRequest 分页请求对象
     * @return 包含对应类型音乐列表的分页对象
     */
    Page<Music> getTypeMusics(Integer userId, String type, PageRequest pageRequest);
}

