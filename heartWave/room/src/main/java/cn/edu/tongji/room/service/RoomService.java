package cn.edu.tongji.room.service;

import cn.edu.tongji.room.dto.CloseRequest;
import cn.edu.tongji.room.dto.MusicRoomDTO;
import org.springframework.stereotype.Service;

@Service
public interface RoomService {
    int createRoom(MusicRoomDTO musicRoomDTO);

    void closeChatRoom(CloseRequest request);
}
