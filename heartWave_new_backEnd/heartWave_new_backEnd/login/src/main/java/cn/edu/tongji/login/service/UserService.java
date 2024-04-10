package cn.edu.tongji.login.service;

import cn.edu.tongji.login.dto.RegisterRequest;
import cn.edu.tongji.login.dto.ResetPasswordRequest;
import cn.edu.tongji.login.dto.SmsInfo;
import cn.edu.tongji.login.model.User;

import java.io.IOException;

public interface UserService {
    User register(RegisterRequest registerRequest) throws IOException;
    boolean checkPhoneAvailable(String phone);
    long checkPhoneUsed(String phone, String username);
    SmsInfo sendSmsCode(String phone);
    void resetPassword(ResetPasswordRequest resetPasswordRequest);
}
