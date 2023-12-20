package cn.edu.tongji.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFriendIntimacyRequest {
    private Integer userId;
    private Integer friendId;
    private Double variation;
}