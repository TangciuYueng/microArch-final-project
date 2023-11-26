package cn.edu.tongji.login.service;

import cn.edu.tongji.login.dto.AddUserRequest;
import cn.edu.tongji.login.dto.UpdateUserRequest;
import cn.edu.tongji.login.model.User;
import cn.edu.tongji.login.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserMapper userMapper;

    public List<User> getAllUsers() {
        return userMapper.selectAll();
    }

    public User getUserById(int id) {
        return userMapper.selectById(id);
    }

    public int addUser(AddUserRequest addUserRequest) {
        return userMapper.addUser(addUserRequest);
    }

    public int updateUser(UpdateUserRequest updateUserRequest) {
        return userMapper.updateUser(updateUserRequest);
    }
}
