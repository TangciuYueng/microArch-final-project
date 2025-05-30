package cn.edu.tongji.musicRoom.service.impl;

import cn.edu.tongji.musicRoom.dto.*;
import cn.edu.tongji.musicRoom.mapper.AdminGroupMapper;
import cn.edu.tongji.musicRoom.mapper.MusicRoomMapper;
import cn.edu.tongji.musicRoom.mapper.MusicRoomMemberMapper;
import cn.edu.tongji.musicRoom.mapper.PublicityMapper;
import cn.edu.tongji.musicRoom.model.AdminGroup;
import cn.edu.tongji.musicRoom.model.MusicRoom;
import cn.edu.tongji.musicRoom.model.MusicRoomMember;
import cn.edu.tongji.musicRoom.model.Publicity;
import cn.edu.tongji.musicRoom.service.MusicRoomService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MusicRoomServiceImpl implements MusicRoomService {
    @Resource
    private MusicRoomMapper musicRoomMapper;
    @Resource
    private MusicRoomMemberMapper musicRoomMemberMapper;
    @Resource
    private AdminGroupMapper adminGroupMapper;
    @Resource
    private PublicityMapper publicityMapper;
    private final static int PAGE_SIZE = 30;

    @Override
    public MusicRoomInfo getAllMusicRoom(Integer page) {
        int startIndex = (page - 1) * PAGE_SIZE;
        List<MusicRoom> musicRooms = musicRoomMapper.getMusicRoomByPage(startIndex, PAGE_SIZE);
        int totalMusicRoom = musicRoomMapper.getAllMusicRoom();
        int totalPage = (int) Math.ceil((double) totalMusicRoom / PAGE_SIZE);

        return new MusicRoomInfo(musicRooms, totalPage, page);
    }

    @Override
    @Transactional
    public MusicRoom createMusicRoom(MusicRoomDTO musicRoomDTO) {
        // 构造要插入的数据
        MusicRoom musicRoom = MusicRoom.builder()
                .title(musicRoomDTO.getTitle())
                .theme(musicRoomDTO.getTheme())
                .description(musicRoomDTO.getDescription())
                .creatorId(musicRoomDTO.getCreatorId())
                .status("open")
                .build();
        // 新增音乐室
        musicRoomMapper.insertMusicRoom(musicRoom);
        int newRoomId = musicRoom.getId();
        int creatorId = musicRoomDTO.getCreatorId();
        // 新增音乐室成员
        musicRoomMemberMapper.insertMusicRoomMember(creatorId, newRoomId);
        // 新增管理员
        adminGroupMapper.insertAdminGroup(creatorId, newRoomId);
        // 判断公开性
        if (musicRoomDTO.getPublicity().equals("limited")) {
            // 不公开批量插入创建者选择的可见用户
            List<Integer> userIds = musicRoomDTO.getUserIds();
            Map<Integer, Integer> userIdAndMusicRoomId = new HashMap<>();
            for (Integer userId : userIds) {
                userIdAndMusicRoomId.put(userId, newRoomId);
            }
            publicityMapper.insertPublicityLimited(userIdAndMusicRoomId);
        } else {
            publicityMapper.insertPublicityPublic(newRoomId);
        }
        // 返回插入后的数据
        return musicRoomMapper.getMusicRoomById(newRoomId);
    }

    @Override
    public MusicRoomDetailed getMusicRoomDetailed(int musicRoomId) {
        MusicRoom musicRoom = musicRoomMapper.getMusicRoomById(musicRoomId);
        List<MusicRoomMember> musicRoomMembers = musicRoomMemberMapper.getMusicRoomMemberByMusicRoomId(musicRoomId);
        List<AdminGroup> adminGroups = adminGroupMapper.getAdminGroupByMusicRoomId(musicRoomId);
        List<Publicity> publicises = publicityMapper.getPublicityByMusicRoomId(musicRoomId);
        List<Integer> userIds = new ArrayList<>();

        for (Publicity publicity: publicises) {
            userIds.add(publicity.getUserId());
        }

        PublicityDTO publicityDTO = PublicityDTO.builder()
                .type(publicises.get(0).getType())
                .userIds(userIds)
                .build();

        MusicRoomDetailed musicRoomDetailed = MusicRoomDetailed.builder()
                .musicRoom(musicRoom)
                .musicRoomMembers(musicRoomMembers)
                .adminGroups(adminGroups)
                .publicity(publicityDTO)
                .build();

        return musicRoomDetailed;
    }

    @Override
    public void closeMusicRoom(int musicRoomId, int operatorId) {
        // 验证是否为音乐室管理员
        List<AdminGroup> adminGroups = adminGroupMapper.getAdminGroupByMusicRoomId(musicRoomId);
        if (!adminGroups.stream().anyMatch(adminGroup -> adminGroup.getUserId() == operatorId)) {
            throw new IllegalArgumentException("Illegal insert, publicity don't allow");
        }
        // 所有成员退出
        List<MusicRoomMember> musicRoomMembers = musicRoomMemberMapper.getMusicRoomMemberByMusicRoomId(musicRoomId);
        for (MusicRoomMember musicRoomMember: musicRoomMembers) {
            musicRoomMemberMapper.setStatus(musicRoomMember.getUserId(), "out");
        }
    }
}
