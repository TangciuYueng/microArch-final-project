package cn.edu.tongji.friend.controller;

import cn.edu.tongji.friend.dto.AddStudentRequest;
import cn.edu.tongji.friend.model.Student;
import cn.edu.tongji.friend.service.RedisService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/redis")
public class RedisController {
    @Resource
    private RedisService redisService;

    @PostMapping("/student")
    public ResponseEntity<?> addStudent(@RequestBody AddStudentRequest addStudentRequest) {
        try {
            redisService.addStudent(addStudentRequest);
            return new ResponseEntity<>("redis successfully add student", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("redis add student failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/student/all")
    public ResponseEntity<?> getStudentAll() {
        try {
            List<Student> students = redisService.getStudentAll();
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("redis get students failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable String id) {
        try {
            Student student = redisService.getStudentById(id);
            return new ResponseEntity<>(student, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("redis get student by id failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<?> deleteStudentById(@PathVariable String id) {
        try {
            redisService.deleteStudentById(id);
            return new ResponseEntity<>("redis successfully delete student by id", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("redis delete student by id failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
