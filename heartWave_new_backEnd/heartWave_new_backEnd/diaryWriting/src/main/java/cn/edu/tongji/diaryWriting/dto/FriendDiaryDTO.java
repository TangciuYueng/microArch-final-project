package cn.edu.tongji.diaryWriting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendDiaryDTO {
    private Integer friendId;
    private Integer diaryId;
}
