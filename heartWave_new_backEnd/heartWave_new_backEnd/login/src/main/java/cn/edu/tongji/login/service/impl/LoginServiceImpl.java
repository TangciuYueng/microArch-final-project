package cn.edu.tongji.login.service.impl;

import cn.edu.tongji.login.dto.VerifyInfo;
import cn.edu.tongji.login.model.User;
import cn.edu.tongji.login.repository.UserRepository;
import cn.edu.tongji.login.service.COSService;
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
    private UserRepository userRepository;
    @Resource
    private EncryptService encryptService;
    @Resource
    private COSService cosService;

    @Override
    public User checkUserLogin(String phone, String name, String password) {
        try {
            List<User> users = userRepository.findAllByPhone(phone);  //根据电话号码查询用户列表

//            如果匹配成功，则执行以下操作：
//            获取匹配用户的 ID，并用该 ID 调用 cosService.getUserAvatar(userId) 方法获取用户头像，并设置到匹配的用户对象中。
//            最后，返回匹配的用户对象。
            if (Objects.equals(users.get(0).getName(), name) && encryptService.passwordCmp(users.get(0).getPassword(), password)) {
                final long userId = users.get(0).getId();
                users.get(0).setAvatar(cosService.getUserAvatar(userId));
                return users.get(0);
            }
            else
                return null;
        } catch (Exception e) {
            return null;
        }
    }


    //获取验证码
    @Override
    public VerifyInfo getVerifyCodeInfo() {
        String verifyCode = VerifyGenerator.getVerifyCode(4);
        String verifyImage = VerifyGenerator.generateVerifyImage(verifyCode);
        return new VerifyInfo(verifyImage, verifyCode);
    }
}
