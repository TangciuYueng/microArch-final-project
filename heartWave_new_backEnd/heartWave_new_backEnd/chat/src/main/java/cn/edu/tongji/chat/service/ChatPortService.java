package cn.edu.tongji.chat.service;

import cn.edu.tongji.chat.dto.ChatResult;
import cn.edu.tongji.chat.dto.EnterChatRoomRequest;

public interface ChatPortService {
    void defaultPort();
    ChatResult enterChatRoom(EnterChatRoomRequest enterChatRoomRequest);
}
