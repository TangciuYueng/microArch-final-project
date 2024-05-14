package cn.edu.tongji.login.controller;

import cn.edu.tongji.login.dto.*;
import cn.edu.tongji.login.model.User;
import cn.edu.tongji.login.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            User user = userService.register(registerRequest);
            return new ResponseEntity<>(user.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("register failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sms/register/{phone}")
    public ResponseEntity<?> sendSmsCodeRegister(@PathVariable("phone") String phone) {
        try {
            if (userService.checkPhoneAvailable(phone)) {
                SmsInfo smsInfo = userService.sendSmsCode(phone);
                return new ResponseEntity<>(smsInfo, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("this phone is already used, try another or login", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("send sms code failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sms/reset-password")
    public ResponseEntity<?> sendSmsCodeSetPassword(
            @RequestParam("phone") String phone,
            @RequestParam("username") String username) {
        try {
            final long userId = userService.checkPhoneUsed(phone, username);

            if (userId != -1) {
                SmsInfoReset smsInfoReset = new SmsInfoReset(userId, userService.sendSmsCode(phone));
                return new ResponseEntity<>(smsInfoReset, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("this phone is not used or username incorrect", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("send sms code failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        try {
            userService.resetPassword(resetPasswordRequest);
            return new ResponseEntity<>("successfully reset password", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("reset password failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/multi")
    public ResponseEntity<?> getMultipleUsers(@RequestParam("ids") List<Long> ids) {
        try {
            List<UserFriendInfo> userFriendInfos = userService.getMultipleUsers(ids);
            return new ResponseEntity<>(userFriendInfos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get multiple users failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/friend-count")
    public ResponseEntity<?> updateFriendCount(@RequestBody UpdateFriendCountRequest updateFriendCountRequest) {
        try {
            userService.updateFriendCount(updateFriendCountRequest);
            return new ResponseEntity<>("successfully update friend count", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("update friend count failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
