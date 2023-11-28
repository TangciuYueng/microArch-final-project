package cn.edu.tongji.login.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private Integer id;
    private String name;
    private String password;
    private String email;
    private String createTime;
    private Integer age;
    private String region;
    private String avatar;
    private String phone;
    private String gender;
}
