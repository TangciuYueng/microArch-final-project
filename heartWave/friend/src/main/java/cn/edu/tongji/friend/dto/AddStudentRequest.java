package cn.edu.tongji.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddStudentRequest {
    private String id;
    private String name;
    private Integer age;
}
