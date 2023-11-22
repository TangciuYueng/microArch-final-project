package cn.edu.tongji.musicroom.service;

import cn.edu.tongji.musicroom.dto.MusicRoomInfo.MusicRoomInfo;
import cn.edu.tongji.musicroom.mapper.MusicRoomMapper;
import cn.edu.tongji.musicroom.model.MusicRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicRoomService {
    private final MusicRoomMapper musicRoomMapper;
    private final int PAGE_SIZE = 30;
    public int insertMusicRoom(MusicRoom musicRoom) {
        return musicRoomMapper.insertMusicRoom(musicRoom);
    }

    public MusicRoomInfo getAllMusicRoom(Integer page) {
        int startIndex = (page - 1) * PAGE_SIZE;
        List<MusicRoom> musicRooms = musicRoomMapper.getMusicRoomByPage(startIndex, PAGE_SIZE);
        int totalMusicRoom = musicRoomMapper.getAllMusicRoom();
        int totalPage = (int) Math.ceil((double) totalMusicRoom / PAGE_SIZE);

        return new MusicRoomInfo(musicRooms, totalPage);
    }
}
// 调用刚刚mapper里的方法去实现里面的业务逻辑