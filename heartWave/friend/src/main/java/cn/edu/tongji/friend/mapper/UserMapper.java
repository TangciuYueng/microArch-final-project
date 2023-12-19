package cn.edu.tongji.friend.mapper;

import cn.edu.tongji.friend.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    User getById(@Param("id") int id);
}
