package cn.edu.tongji.friend.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Friend {
    private Integer id;
    private Integer userId;
    private Integer friendId;
    private Double intimacy;
}
