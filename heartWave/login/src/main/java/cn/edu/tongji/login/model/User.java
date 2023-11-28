package cn.edu.tongji.login.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class User {
    private Integer id;
    private String name;
    private String password;
    private String email;
    private LocalDateTime createTime;
    private Integer age;
    private String region;
    private String avatar;
    private String phone;
    private String gender;
}
