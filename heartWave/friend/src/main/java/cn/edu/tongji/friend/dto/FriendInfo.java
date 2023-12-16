package cn.edu.tongji.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendInfo {
    private Integer id;
    private String name;
    private String avatar;
    private Double intimacy;
}
