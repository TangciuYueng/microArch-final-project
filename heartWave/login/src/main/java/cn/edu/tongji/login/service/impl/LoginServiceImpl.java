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
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private EncryptService encryptService;
    @Resource
    private UserMapper userMapper;

    @Override
    public User checkUserLogin(String phone, String name, String password) {
        try {
            List<User> users = userMapper.getByPhone(phone);

            if (Objects.equals(users.get(0).getName(), name) && encryptService.passwordCmp(users.get(0).getPassword(), password))
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
