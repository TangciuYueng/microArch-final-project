package cn.edu.tongji.musicRoom.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRecord {
    @Id
    private int id;
    private String content;
    private String type;
    private int userId;
    private int musicRoomId;
    private boolean isDeleted;
}
