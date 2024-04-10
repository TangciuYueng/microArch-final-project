package cn.edu.tongji.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String phone;
    private String password;
    private String email;
    private Integer age;
    private String region;
    private String avatar;
    private String gender;
}
