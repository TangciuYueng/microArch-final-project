package cn.edu.tongji.friend.repository;

import cn.edu.tongji.friend.model.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, String> {
    //不用编写，有一套已实现的基本方法
}
