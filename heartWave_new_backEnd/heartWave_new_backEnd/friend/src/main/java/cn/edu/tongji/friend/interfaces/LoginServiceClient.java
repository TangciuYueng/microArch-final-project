package cn.edu.tongji.friend.interfaces;

import cn.edu.tongji.friend.dto.UpdateFriendCountRequest;
import cn.edu.tongji.friend.dto.UserFriendInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "login-service", path = "/api/user")
public interface LoginServiceClient {
    @GetMapping("/multi")
    List<UserFriendInfo> getMultipleUsers(@RequestParam("ids") List<Long> ids);

    @PutMapping("/friend-count")
    void updateFriendCount(@RequestBody UpdateFriendCountRequest updateFriendCountRequest);
}
