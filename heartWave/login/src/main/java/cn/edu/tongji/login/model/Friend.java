package cn.edu.tongji.login.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friend {
    private Integer id;
    private Integer userId;
    private Integer friendId;
    private Double intimacy;
}
