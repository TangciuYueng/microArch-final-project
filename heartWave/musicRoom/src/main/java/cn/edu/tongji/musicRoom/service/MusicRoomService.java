package cn.edu.tongji.musicRoom.service;

import cn.edu.tongji.musicRoom.dto.MusicRoomInfo;
import cn.edu.tongji.musicRoom.mapper.MusicRoomMapper;
import cn.edu.tongji.musicRoom.model.MusicRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicRoomService {
    private final MusicRoomMapper musicRoomMapper;
    private final static int PAGE_SIZE = 30;
    public int insertMusicRoom(MusicRoom musicRoom) {
        return musicRoomMapper.insertMusicRoom(musicRoom);
    }

    public MusicRoomInfo getAllMusicRoom(Integer page) {
        int startIndex = (page - 1) * PAGE_SIZE;
        List<MusicRoom> musicRooms = musicRoomMapper.getMusicRoomByPage(startIndex, PAGE_SIZE);
        int totalMusicRoom = musicRoomMapper.getAllMusicRoom();
        int totalPage = (int) Math.ceil((double) totalMusicRoom / PAGE_SIZE);

        return new MusicRoomInfo(musicRooms, totalPage, page);
    }
}
