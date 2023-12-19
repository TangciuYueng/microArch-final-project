package cn.edu.tongji.login.controller;

import cn.edu.tongji.login.dto.FriendInfo;
import cn.edu.tongji.login.dto.UpdateFriendIntimacyRequest;
import cn.edu.tongji.login.model.Friend;
import cn.edu.tongji.login.service.FriendService;
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

    @GetMapping("/{userId}")
    public ResponseEntity<?> getFriendsByUserId(@PathVariable("userId") int userId) {
        try {
            List<FriendInfo> friendInfos = friendService.getFriendsByUserId(userId);
            return new ResponseEntity<>(friendInfos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get friends by user id failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{userId}/{friendId}")
    public ResponseEntity<?> addFriend(@PathVariable("userId") int userId, @PathVariable("friendId") int friendId) {
        try {
            List<Friend> friends = friendService.addFriend(userId, friendId);
            return new ResponseEntity<>(friends, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("add friend failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateFriendIntimacy(@RequestBody UpdateFriendIntimacyRequest updateFriendIntimacyRequest) {
        try {
            double newIntimacy = friendService.updateFriendIntimacy(updateFriendIntimacyRequest);
            return new ResponseEntity<>(newIntimacy, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("update friend intimacy failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{userId}/{friendId}")
    public ResponseEntity<?> deleteFriend(@PathVariable("userId") int userId, @PathVariable("friendId") int friendId) {
        try {
            friendService.deleteFriend(userId, friendId);
            return new ResponseEntity<>("successfully delete friend", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("delete friend failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
