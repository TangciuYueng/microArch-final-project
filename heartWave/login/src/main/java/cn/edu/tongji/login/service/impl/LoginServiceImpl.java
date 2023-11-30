package cn.edu.tongji.login.service.impl;

import cn.edu.tongji.login.dto.UserLoginRequest;
import cn.edu.tongji.login.dto.VerifyInfo;
import cn.edu.tongji.login.mapper.UserMapper;
import cn.edu.tongji.login.model.User;
import cn.edu.tongji.login.service.EncryptService;
import cn.edu.tongji.login.service.LoginService;
import cn.edu.tongji.login.utils.VerifyGenerator;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private EncryptService encryptService;
    @Resource
    private UserMapper userMapper;

    @Override
    public User checkUserLogin(UserLoginRequest userLoginRequest) {
        try {
            List<User> users = userMapper.getByPhone(userLoginRequest.getPhone());

            if (encryptService.passwordCmp(users.get(0).getPassword(), userLoginRequest.getPassword()))
                return users.get(0);
            else
                return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public VerifyInfo getVerifyCodeInfo() {
        String verifyCode = VerifyGenerator.getVerifyCode(4);
        String verifyImage = VerifyGenerator.generateVerifyImage(verifyCode);
        return new VerifyInfo("data:image/jpg;base64," + verifyImage, verifyCode);
    }
}
