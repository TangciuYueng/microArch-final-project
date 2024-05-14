package cn.edu.tongji.login.service;

import cn.edu.tongji.login.dto.*;
import cn.edu.tongji.login.model.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    User register(RegisterRequest registerRequest) throws IOException;
    boolean checkPhoneAvailable(String phone);
    long checkPhoneUsed(String phone, String username);
    SmsInfo sendSmsCode(String phone);
    void resetPassword(ResetPasswordRequest resetPasswordRequest);
    List<UserFriendInfo> getMultipleUsers(List<Long> ids);
    void updateFriendCount(UpdateFriendCountRequest updateFriendCountRequest);
}
