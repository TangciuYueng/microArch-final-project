package cn.edu.tongji.friend.service;

import cn.edu.tongji.friend.dto.AddStudentRequest;
import cn.edu.tongji.friend.model.Student;

import java.util.List;

public interface RedisService {
    Student getStudentById(String id);
    List<Student> getStudentAll();
    void addStudent(AddStudentRequest addStudentRequest);
    void deleteStudentById(String id);
}
