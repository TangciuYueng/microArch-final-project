package cn.edu.tongji.login.dto;

import cn.edu.tongji.login.model.GenderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserInfo {
    private Long id;
    private String name;
    private String avatar;
    private GenderType gender;
    private Integer age;
    private String email;
    private String region;
}
