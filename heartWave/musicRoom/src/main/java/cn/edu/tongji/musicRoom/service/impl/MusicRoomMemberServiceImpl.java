package cn.edu.tongji.musicRoom.service.impl;

import cn.edu.tongji.musicRoom.dto.MemberChatableRequest;
import cn.edu.tongji.musicRoom.dto.MemberRequest;
import cn.edu.tongji.musicRoom.dto.MemberSingableRequest;
import cn.edu.tongji.musicRoom.mapper.AdminGroupMapper;
import cn.edu.tongji.musicRoom.mapper.MusicRoomMemberMapper;
import cn.edu.tongji.musicRoom.mapper.PublicityMapper;
import cn.edu.tongji.musicRoom.model.AdminGroup;
import cn.edu.tongji.musicRoom.model.MusicRoomMember;
import cn.edu.tongji.musicRoom.model.Publicity;
import cn.edu.tongji.musicRoom.service.MusicRoomMemberService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicRoomMemberServiceImpl implements MusicRoomMemberService {
    @Resource
    private MusicRoomMemberMapper musicRoomMemberMapper;
    @Resource
    private PublicityMapper publicityMapper;
    @Resource
    private AdminGroupMapper adminGroupMapper;

    private final static int PAGE_SIZE = 30;

    @Override
    public MusicRoomMember addMusicRoomMember(MemberRequest request) {
        int userId = request.getUserId();
        int musicRoomId = request.getMusicRoomId();

        // 获取音乐室公开状态
        String type = publicityMapper.getPublicityTypeByMusicRoomId(musicRoomId);
        if (type == null) {
            throw new IllegalStateException("No corresponded publicity");
        }

        boolean valid = false;
        // 判断插入是否合法
        if (type.equals("public")) {
            valid = true;
        } else {
            List<Publicity> publicises = publicityMapper.getPublicityByMusicRoomId(musicRoomId);
            valid = publicises.stream().anyMatch(publicity -> userId == publicity.getUserId());
        }

        if (!valid) {
            throw new IllegalArgumentException("Illegal insert, publicity don't allow");
        }

        // 合法插入
        MusicRoomMember musicRoomMember = MusicRoomMember.builder()
                .musicRoomId(musicRoomId)
                .userId(userId)
                .chatable(true)
                .singable(true)
                .status("in")
                .build();
        musicRoomMemberMapper.insertMusicRoomMemberByInstance(musicRoomMember);
        return musicRoomMember;
    }

    @Override
    public void updateMemberSingable(int memberId, MemberSingableRequest request) {
        int operatorId = request.getOperatorId();
        int musicRoomId = musicRoomMemberMapper.getMusicRoomIdById(memberId);
        // 验证是否为音乐室管理员
        List<AdminGroup> adminGroups = adminGroupMapper.getAdminGroupByMusicRoomId(musicRoomId);
        if (!adminGroups.stream().anyMatch(adminGroup -> adminGroup.getUserId() == operatorId)) {
            throw new IllegalArgumentException("Illegal insert, publicity don't allow");
        }
        // 合法修改
        musicRoomMemberMapper.setSingable(memberId, request.isSingable()? 1: 0);
    }

    @Override
    public void updateMemberChatable(int memberId, MemberChatableRequest request) {
        int operatorId = request.getOperatorId();
        int musicRoomId = musicRoomMemberMapper.getMusicRoomIdById(memberId);
        // 验证是否为音乐室管理员
        List<AdminGroup> adminGroups = adminGroupMapper.getAdminGroupByMusicRoomId(musicRoomId);
        System.out.println(adminGroups);
        if (!adminGroups.stream().anyMatch(adminGroup -> adminGroup.getUserId() == operatorId)) {
            throw new IllegalArgumentException("Illegal insert, publicity don't allow");
        }
        // 合法修改
        musicRoomMemberMapper.setChatable(memberId, request.isChatable()? 1: 0);
    }

    @Override
    public void updateMemberStatus(int memberId, boolean in) {
        musicRoomMemberMapper.setStatus(memberId, in? "in": "out");
    }
}
