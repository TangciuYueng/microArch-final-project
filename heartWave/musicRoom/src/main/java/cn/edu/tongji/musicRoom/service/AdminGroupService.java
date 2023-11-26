package cn.edu.tongji.musicRoom.service;

import cn.edu.tongji.musicRoom.dto.AdminRequest;
import cn.edu.tongji.musicRoom.mapper.AdminGroupMapper;
import cn.edu.tongji.musicRoom.model.AdminGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminGroupService {
    private final AdminGroupMapper adminGroupMapper;

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
