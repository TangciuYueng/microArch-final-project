package cn.edu.tongji.login.controller;

import cn.edu.tongji.login.dto.FriendInfo;
import cn.edu.tongji.login.dto.UpdateFriendIntimacyRequest;
import cn.edu.tongji.login.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friend")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @GetMapping("/list/{userId}")
    public ResponseEntity<List<FriendInfo>> getFriendByUserId(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(friendService.getFriendByUserId(userId));
    }

    @PostMapping("/add/{userId}/{friendId}")
    public boolean addFriend(@PathVariable("userId") int userId, @PathVariable("friendId") int friendId) {
        return friendService.addFriend(userId, friendId);
    }

    @PutMapping("/update")
    public int updateFriendIntimacy(@RequestBody UpdateFriendIntimacyRequest updateFriendIntimacyRequest) {
        return friendService.updateFriendIntimacy(updateFriendIntimacyRequest);
    }

    @DeleteMapping("/delete/{userId}/{friendId}")
    public boolean deleteFriend(@PathVariable("userId") int userId, @PathVariable("friendId") int friendId) {
        return friendService.deleteFriend(userId, friendId);
    }
}
