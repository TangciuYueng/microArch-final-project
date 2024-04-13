package cn.edu.tongji.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendInfo {
    private Long friendId;
    private Double intimacy;
    private LocalDateTime chatTime;
    private String name;
    private String avatar;
}
