package cn.edu.tongji.musicRoom.service;


import cn.edu.tongji.musicRoom.mapper.MusicRoomMemberMapper;
import cn.edu.tongji.musicRoom.model.MusicRoomMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicRoomMemberService {
    private final MusicRoomMemberMapper musicRoomMemberMapper;
    public void insertMusicRoomMember(int userId, int musicRoomId) {
        musicRoomMemberMapper.insertMusicRoomMember(userId, musicRoomId);
    }
    public List<MusicRoomMember> getMusicRoomMemberByMusicRoomId(int musicRoomId) {
        return musicRoomMemberMapper.getMusicRoomMemberByMusicRoomId(musicRoomId);
    }
    public void setStatusOut(int userId, int musicRoomId) {
        musicRoomMemberMapper.setStatusOut(userId, musicRoomId);
    }
    public void setStatusIn(int userId, int musicRoomId) {
        musicRoomMemberMapper.setStatusIn(userId, musicRoomId);
    }
    public void setSingable(int userId, int musicRoomId) {
        musicRoomMemberMapper.setSingable(userId, musicRoomId);
    }

    public void setNoSingable(int userId, int musicRoomId) {
        musicRoomMemberMapper.setNoSingable(userId, musicRoomId);
    }

    public void setChatable(int userId, int musicRoomId) {
        musicRoomMemberMapper.setChatable(userId, musicRoomId);
    }

    public void setNoChatable(int userId, int musicRoomId) {
        musicRoomMemberMapper.setNoChatable(userId, musicRoomId);
    }
}
