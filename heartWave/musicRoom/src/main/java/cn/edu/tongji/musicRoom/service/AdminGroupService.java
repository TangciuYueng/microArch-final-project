package cn.edu.tongji.musicRoom.service;

import cn.edu.tongji.musicRoom.dto.AdminRequest;
import cn.edu.tongji.musicRoom.model.AdminGroup;
import org.springframework.stereotype.Service;

@Service
public interface AdminGroupService {
    AdminGroup addAdminGroup(AdminRequest request);
}
