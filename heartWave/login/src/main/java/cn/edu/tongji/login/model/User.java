package cn.edu.tongji.login.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
