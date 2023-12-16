package cn.edu.tongji.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteFriendRequest {
    private Integer userId;
    private Integer friendId;
}
