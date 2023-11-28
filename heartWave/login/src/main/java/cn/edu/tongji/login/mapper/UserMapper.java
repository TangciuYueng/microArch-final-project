package cn.edu.tongji.login.mapper;

import cn.edu.tongji.login.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    List<User> getAll();
    User getById(@Param("id") int id);
    int add(User user);
    int update(User user);
}
