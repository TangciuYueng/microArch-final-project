package cn.edu.tongji.friend.controller;

import cn.edu.tongji.friend.dto.AddFriendRequest;
import cn.edu.tongji.friend.dto.FriendInfo;
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

    @GetMapping
    public ResponseEntity<?> getFriends(@RequestParam("userId") long userId) {
        try {
            List<FriendInfo> friendInfos = friendService.getFriends(userId);
            return new ResponseEntity<>(friendInfos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get friends failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> addFriend(@RequestBody AddFriendRequest addFriendRequest) {
        try {
            friendService.addFriend(addFriendRequest);
            return new ResponseEntity<>("successfully added friend", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("add friend failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
