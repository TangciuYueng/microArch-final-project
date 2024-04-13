package cn.edu.tongji.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddFriendRequest {
    private Long userId1;
    private Long userId2;
}
