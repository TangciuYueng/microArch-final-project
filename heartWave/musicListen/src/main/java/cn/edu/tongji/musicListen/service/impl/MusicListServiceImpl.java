package cn.edu.tongji.musicListen.service.impl;

import cn.edu.tongji.musicListen.mapper.MusicListMapper;
import cn.edu.tongji.musicListen.model.MusicList;
import cn.edu.tongji.musicListen.service.MusicListService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MusicListServiceImpl implements MusicListService {
    @Resource
    private MusicListMapper musicListMapper;
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

}
