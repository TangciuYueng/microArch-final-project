package cn.edu.tongji.musicListen.service.impl;

import cn.edu.tongji.musicListen.dto.MultiMusicMusicListRequest;
import cn.edu.tongji.musicListen.mapper.MusicListMapper;
import cn.edu.tongji.musicListen.mapper.PlayCountMapper;
import cn.edu.tongji.musicListen.model.MusicList;
import cn.edu.tongji.musicListen.model.PlayCount;
import cn.edu.tongji.musicListen.service.MusicListService;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MusicListServiceImpl implements MusicListService {
    @Resource
    private MusicListMapper musicListMapper;
    @Resource
    private PlayCountMapper playCountMapper;
    @Override
    public int insertMusicList(MusicList musicList){
        System.out.println(musicList);
        return musicListMapper.insertMusicList(musicList);
    }

    @Override
    public Map<String, List<MusicList>> getMusicListByUserId(int userId) {
        var musicLists = musicListMapper.getMusicListByUserId(userId);
        Map<String, List<MusicList>> typeMap = musicLists.stream()
                .collect(Collectors.groupingBy(MusicList::getType));

        // 打印结果
        typeMap.forEach((type, list) -> {
            System.out.println("Type: " + type);
            list.forEach(System.out::println);
            System.out.println("-----------");
        });
        return typeMap;
    }

    @Override
    public Map<String, List<MusicList>> getListenRecordListByUserId(int userId) {
        var musicLists = musicListMapper.getListenRecordListByUserId(userId);

        // 先得到 userId 和 musicId
        List<PlayCount> playCounts = musicLists.stream()
                .map(musicList -> PlayCount.builder()
                        .userId(musicList.getUserId())
                        .musicId(musicList.getMusicId())
                        .build())
                .collect(Collectors.toList());

        // 查询得到 PlayCount
        List<PlayCount> playCounts1 = playCountMapper.getByUserIdMusicId(playCounts);

        // 更改 playCount 为 playCount 里面的 play_count
        musicLists.forEach(musicList -> {
            playCounts1.stream()
                    .filter(playCount -> musicList.getMusicId() == playCount.getMusicId()
                            && musicList.getUserId() == playCount.getUserId())
                    .findFirst()
                    .ifPresent(playCount -> musicList.setPlayCount(playCount.getPlayCount()));
        });

        // 构造返回值
        Map<String, List<MusicList>> typeMap = musicLists.stream()
                .collect(Collectors.groupingBy(MusicList::getType));

        // 打印结果
        typeMap.forEach((type, list) -> {
            System.out.println("Type2: " + type);
            list.forEach(System.out::println);
            System.out.println("-----------");
        });
        return typeMap;
    }

    @Override
    @Transactional
    public List<Integer> insertMusicList(MultiMusicMusicListRequest request) {
        List<MusicList> musicLists = convertToMusicListArray(request);
        musicListMapper.batchInsertMusicList(musicLists);
        System.out.println(musicLists);
        return musicListMapper.getGeneratedIds(musicLists);
    }

    private List<MusicList> convertToMusicListArray(MultiMusicMusicListRequest request) {
        List<MusicList> musicLists = new ArrayList<>();
        for (int i = 0; i < request.getMusicIds().size(); ++i) {
            musicLists.add(MusicList.builder()
                    .userId(request.getUserId())
                    .musicId(request.getMusicIds().get(i))
                    .description(request.getDescription())
                    .type(request.getType())
                    .build());
        }
        return musicLists;
    }

}
