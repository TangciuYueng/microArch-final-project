package cn.edu.tongji.login.service;

import cn.edu.tongji.login.dto.AddUserRequest;
import cn.edu.tongji.login.dto.SmsInfo;
import cn.edu.tongji.login.dto.UpdateUserRequest;
import cn.edu.tongji.login.dto.UserInfo;
import cn.edu.tongji.login.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> getAllUsers();
    UserInfo getUserInfoById(int id);
    User addUser(AddUserRequest addUserRequest);
    void updateUser(UpdateUserRequest updateUserRequest);
    boolean checkPhoneAvailable(String phone);
    SmsInfo sendSmsCode(String phone);
}
