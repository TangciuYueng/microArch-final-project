package cn.edu.tongji.login.controller;

import cn.edu.tongji.login.dto.UserLoginRequest;
import cn.edu.tongji.login.dto.VerifyInfo;
import cn.edu.tongji.login.model.User;
import cn.edu.tongji.login.service.LoginService;
import cn.edu.tongji.login.service.SmsService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Resource
    private LoginService loginService;
    @Resource
    private SmsService smsService;

    @GetMapping("/user")
    public ResponseEntity<?> userLogin(@RequestBody UserLoginRequest userLoginRequest) {
        try {
            User user = loginService.checkUserLogin(userLoginRequest);

            if (user != null)
                return new ResponseEntity<>(user, HttpStatus.OK);
            else
                return new ResponseEntity<>("password wrong or user not found", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("user login failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/verify_info")
    public ResponseEntity<?> getVerifyInfo() {
        try {
            VerifyInfo verifyInfo = loginService.getVerifyCodeInfo();
            return new ResponseEntity<>(verifyInfo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get verify info failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
