package cn.edu.tongji.musicRoom.controller;

import cn.edu.tongji.musicRoom.dto.AdminRequest;
import cn.edu.tongji.musicRoom.model.AdminGroup;
import cn.edu.tongji.musicRoom.service.AdminGroupService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin-group")
public class AdminGroupController {
    @Resource
    private AdminGroupService adminGroupService;

    @PostMapping
    public ResponseEntity<?> addAdminGroup(@RequestBody AdminRequest request) {
        try {
            AdminGroup adminGroup = adminGroupService.addAdminGroup(request);
            return new ResponseEntity<>(adminGroup, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            String errMsg = "add music room admin failed";
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
