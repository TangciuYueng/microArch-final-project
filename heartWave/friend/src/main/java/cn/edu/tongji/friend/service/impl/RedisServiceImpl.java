package cn.edu.tongji.friend.service.impl;

import cn.edu.tongji.friend.dto.AddStudentRequest;
import cn.edu.tongji.friend.model.Student;
import cn.edu.tongji.friend.repository.StudentRepository;
import cn.edu.tongji.friend.service.RedisService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisServiceImpl implements RedisService {
    @Resource
    private StudentRepository studentRepository;

    @Override
    public Student getStudentById(String id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Student> getStudentAll() {
        return (List<Student>) studentRepository.findAll();
    }

    @Override
    public void addStudent(AddStudentRequest addStudentRequest) {
        studentRepository.save(Student.builder()
                .id(addStudentRequest.getId())
                .name(addStudentRequest.getName())
                .age(addStudentRequest.getAge())
                .build()
        );
    }

    @Override
    public void deleteStudentById(String id) {
        studentRepository.deleteById(id);
    }
}
