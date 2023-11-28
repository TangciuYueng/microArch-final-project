package cn.edu.tongji.musicRoom.service.impl;

import cn.edu.tongji.musicRoom.dto.AdminRequest;
import cn.edu.tongji.musicRoom.mapper.AdminGroupMapper;
import cn.edu.tongji.musicRoom.model.AdminGroup;
import cn.edu.tongji.musicRoom.service.AdminGroupService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminGroupServiceImpl implements AdminGroupService {
    @Resource
    private AdminGroupMapper adminGroupMapper;
    @Override
    public AdminGroup addAdminGroup(AdminRequest request) {
        int operatorId = request.getOperatorId();
        int musicRoomId = request.getMusicRoomId();

        // 验证是否为音乐室管理员
        List<AdminGroup> adminGroups = adminGroupMapper.getAdminGroupByMusicRoomId(musicRoomId);
        if (!adminGroups.stream().anyMatch(adminGroup -> adminGroup.getUserId() == operatorId)) {
            throw new IllegalArgumentException("Illegal insert, publicity don't allow");
        }

        // 合法增加管理员
        AdminGroup adminGroup = AdminGroup.builder()
                .musicRoomId(musicRoomId)
                .userId(request.getUserId())
                .build();
        adminGroupMapper.insertAdminGroupByInstance(adminGroup);
        return adminGroup;
    }
}
