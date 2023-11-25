package cn.edu.tongji.login.mapper;

import cn.edu.tongji.login.dto.AddUserRequest;
import cn.edu.tongji.login.dto.UpdateUserRequest;
import cn.edu.tongji.login.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    List<User> selectAll();
    User selectById(int id);
    int addUser(AddUserRequest addUserRequest);
    int updateUser(UpdateUserRequest updateUserRequest);
}
