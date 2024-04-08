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

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get all users failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") int id) {
        try {
            User user = userService.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get user by id failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/many")
    public ResponseEntity<?> getUsersByIds(@RequestParam("ids") List<Integer> ids) {
        try {
            List<UserFriendInfo> userFriendInfos = userService.getUsersByIds(ids);
            return new ResponseEntity<>(userFriendInfos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get users by ids failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<?> getUserInfoById(@PathVariable("id") int id) {
        try {
            UserInfo userInfo = userService.getUserInfoById(id);
            return new ResponseEntity<>(userInfo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get user info by id failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*已使用*/
    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody AddUserRequest addUserRequest) {
        try {
            User user = userService.addUser(addUserRequest);
            return new ResponseEntity<>(user.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("add user failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        try {
            userService.updateUser(updateUserRequest);
            return ResponseEntity.ok("successfully update user");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("update user failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*已使用*/
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

    /*已使用*/
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

    /*已使用*/
    @GetMapping("/sms/reset-password")
    public ResponseEntity<?> sendSmsCodeSetPassword(
            @RequestParam("phone") String phone,
            @RequestParam("username") String username) {
        try {
            final int userId = userService.checkPhoneUsed(phone, username);

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
}
