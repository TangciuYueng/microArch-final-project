package cn.edu.tongji.login.controller;

import cn.edu.tongji.login.dto.VerifyInfo;
import cn.edu.tongji.login.model.User;
import cn.edu.tongji.login.service.LoginService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Resource
    private LoginService loginService;

    @GetMapping
    public ResponseEntity<?> userLogin(
            @RequestParam("phone") String phone,
            @RequestParam("name") String name,
            @RequestParam("password") String password) {
        try {
            User user = loginService.checkUserLogin(phone, name, password);

            if (user != null)
                return new ResponseEntity<>(user, HttpStatus.OK);
            else
                return new ResponseEntity<>("password wrong or user not found", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("user login failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/verify-info")
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
