package cn.edu.tongji.musicRoom.service;


import cn.edu.tongji.musicRoom.dto.*;
import cn.edu.tongji.musicRoom.model.MusicRoomMember;
import org.springframework.stereotype.Service;

@Service
public interface MusicRoomMemberService {
    MusicRoomMember addMusicRoomMember(MemberRequest request);
    void updateMemberSingable(int memberId, MemberSingableRequest request);
    void updateMemberChatable(int memberId, MemberChatableRequest request);
    void updateMemberStatus(int memberId, boolean in);
}
