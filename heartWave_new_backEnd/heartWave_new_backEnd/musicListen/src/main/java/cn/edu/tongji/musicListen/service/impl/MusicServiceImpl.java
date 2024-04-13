package cn.edu.tongji.musicListen.service.impl;

import cn.edu.tongji.musicListen.domain.Music;
import cn.edu.tongji.musicListen.domain.MusicList;
import cn.edu.tongji.musicListen.repository.MusicListRepository;
import cn.edu.tongji.musicListen.repository.MusicListUserRelationshipRepository;
import cn.edu.tongji.musicListen.repository.MusicRepository;
import cn.edu.tongji.musicListen.service.MusicService;
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
    @Autowired
    private MusicListUserRelationshipRepository musicListUserRelationshipRepository;
    @Autowired
    private MusicListRepository musicListRepository;

    /**
     * 根据音乐ID获取音乐信息
     *
     * @param id 音乐ID
     * @return 包含音乐信息的Optional对象
     */
    @Override
    public Optional<Music> getMusicById(Integer id) {
        return musicRepository.findById(id);
    }

    /**
     * 分页获取所有音乐信息
     *
     * @param pageRequest 分页请求对象
     * @return 包含音乐分页信息的Page对象
     */
    @Override
    public Page<Music> findAllByPage(PageRequest pageRequest) {
        return musicRepository.findAll(pageRequest);
    }

    /**
     * 根据标题关键词分页搜索音乐信息
     *
     * @param pageRequest 分页请求对象
     * @param title       音乐标题关键词
     * @return 包含搜索结果的Page对象
     */
    @Override
    public Page<Music> findMusicByTitle(PageRequest pageRequest, String title) {
        return musicRepository.findByTitleContaining(pageRequest, title);
    }

     /**
     * 根据音乐ID列表查询对应的音乐信息并进行分页返回
     *
     * @param musicIds 包含音乐ID的列表
     * @param pageRequest 分页请求对象
     * @return 包含音乐信息的分页对象
     */
    @Override
    public Page<Music> getTypeMusics(Integer userId, String type, PageRequest pageRequest) {
        // 查询出最新的 musicListId
        Integer latestMusicListId = musicListUserRelationshipRepository.findLatestMusicListIdByUserIdAndType(userId, type);
        // 通过该 id 查询对应 trackEntry 中的 musicIds
        Optional<MusicList> latestMusicList = musicListRepository.findById(latestMusicListId);
        List<Integer> musicIds = latestMusicList.get().getEntries().stream().map(entry -> entry.getMusicId()).toList();
        // 通过 musicIds 获取对应 music
        return musicRepository.findMusicByIds(musicIds, pageRequest);
    }
}
