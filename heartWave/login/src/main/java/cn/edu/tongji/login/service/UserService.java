package cn.edu.tongji.login.service;

import cn.edu.tongji.login.dto.*;
import cn.edu.tongji.login.model.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface UserService {
    List<User> getAllUsers();
    List<UserFriendInfo> getUsersByIds(List<Integer> ids);
    User getUserById(int id);
    UserInfo getUserInfoById(int id);
    User addUser(AddUserRequest addUserRequest) throws IOException;
    void updateUser(UpdateUserRequest updateUserRequest);
    void resetPassword(ResetPasswordRequest resetPasswordRequest);
    boolean checkPhoneAvailable(String phone);
    int checkPhoneUsed(String phone, String username);
    SmsInfo sendSmsCode(String phone);
}
