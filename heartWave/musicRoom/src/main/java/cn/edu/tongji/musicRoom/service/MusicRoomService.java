package cn.edu.tongji.musicRoom.service;

import cn.edu.tongji.musicRoom.dto.CloseRequest;
import cn.edu.tongji.musicRoom.dto.MusicRoomDTO;
import cn.edu.tongji.musicRoom.dto.MusicRoomDetailed;
import cn.edu.tongji.musicRoom.dto.MusicRoomInfo;
import cn.edu.tongji.musicRoom.model.MusicRoom;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface MusicRoomService {
    MusicRoomInfo getAllMusicRoom(Integer page);

    @Transactional
    MusicRoom createMusicRoom(MusicRoomDTO musicRoomDTO);

    MusicRoomDetailed getMusicRoomDetailed(int musicRoomId);

    void closeMusicRoom(CloseRequest closeRequest);
}
