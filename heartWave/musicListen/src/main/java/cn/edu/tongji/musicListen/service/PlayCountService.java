package cn.edu.tongji.musicListen.service;

import cn.edu.tongji.musicListen.model.PlayCount;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlayCountService {
    // 通过userid获取该用户听过歌曲的所有播放次数
    List<PlayCount> getListenRecordListByUserId(int userId);
    // 添加一条播放记录
    int  insertPlayCount(PlayCount playCount);

}
