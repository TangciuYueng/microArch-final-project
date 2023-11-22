package cn.edu.tongji.musicRoom.dto;

import cn.edu.tongji.musicRoom.model.ChatRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRecordInfo {
    private List<ChatRecord> chatRecords;
    private int totalPage;
    private int currentPage;
}
