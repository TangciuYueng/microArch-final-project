package cn.edu.tongji.login.service;

import cn.edu.tongji.login.dto.UserLoginRequest;
import cn.edu.tongji.login.dto.VerifyInfo;
import cn.edu.tongji.login.model.User;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    User checkUserLogin(UserLoginRequest userLoginRequest);
    VerifyInfo getVerifyCodeInfo();
}
