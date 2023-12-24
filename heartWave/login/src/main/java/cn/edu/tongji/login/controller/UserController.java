package cn.edu.tongji.login.controller;

import cn.edu.tongji.login.dto.AddUserRequest;
import cn.edu.tongji.login.dto.SmsInfo;
import cn.edu.tongji.login.dto.UpdateUserRequest;
import cn.edu.tongji.login.dto.UserInfo;
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

    @GetMapping("info/{id}")
    public ResponseEntity<?> getUserInfoById(@PathVariable("id") int id) {
        try {
            UserInfo userInfo = userService.getUserInfoById(id);
            return new ResponseEntity<>(userInfo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get user info by id failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody AddUserRequest addUserRequest) {
        try {
            User user = userService.addUser(addUserRequest);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
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

    @GetMapping("/sms/{phone}")
    public ResponseEntity<?> sendSmsCode(@PathVariable("phone") String phone) {
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
}
