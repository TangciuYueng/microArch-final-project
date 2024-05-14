package cn.edu.tongji.friend.controller;

import cn.edu.tongji.friend.dto.AddFriendRequest;
import cn.edu.tongji.friend.dto.FriendInfo;
import cn.edu.tongji.friend.dto.UpdateFriendCountRequest;
import cn.edu.tongji.friend.dto.UserFriendInfo;
import cn.edu.tongji.friend.interfaces.LoginServiceClient;
import cn.edu.tongji.friend.service.FriendService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friend")
public class FriendController {
    @Resource
    private FriendService friendService;
    @Resource
    private LoginServiceClient loginServiceClient;

    @PostMapping
    public ResponseEntity<?> addFriend(@RequestBody AddFriendRequest addFriendRequest) {
        try {
            friendService.addFriend(addFriendRequest);
            loginServiceClient.updateFriendCount(new UpdateFriendCountRequest(addFriendRequest.getUserId1(), 1));
            loginServiceClient.updateFriendCount(new UpdateFriendCountRequest(addFriendRequest.getUserId2(), 1));
            return new ResponseEntity<>("successfully added friend", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("add friend failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getFriends(@RequestParam("userId") long userId) {
        try {
            //跨服务调用login，得到user表中的好友信息
            List<Long> friendIds = friendService.getFriendIds(userId);
            List<UserFriendInfo> userFriendInfos = loginServiceClient.getMultipleUsers(friendIds);

            List<FriendInfo> friendInfos = friendService.getFriends(userId, userFriendInfos);
            return new ResponseEntity<>(friendInfos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get friends failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
