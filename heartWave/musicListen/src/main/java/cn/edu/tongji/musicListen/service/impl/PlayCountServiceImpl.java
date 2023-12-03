package cn.edu.tongji.musicListen.service.impl;

import cn.edu.tongji.musicListen.mapper.MusicMapper;
import cn.edu.tongji.musicListen.mapper.PlayCountMapper;
import cn.edu.tongji.musicListen.model.PlayCount;
import cn.edu.tongji.musicListen.service.PlayCountService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PlayCountServiceImpl implements PlayCountService {

    @Resource
    private PlayCountMapper playCountMapper;
    @Override
    public List<PlayCount> getListenRecordListByUserId(int userId){
        return playCountMapper.getListenRecordListByUserId(userId);
    }

    @Override
    public int insertPlayCount(PlayCount playCount){
        return playCountMapper.insertPlayCount(playCount);
    }

}
