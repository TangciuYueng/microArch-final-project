package cn.edu.tongji.chat.controller;

import cn.edu.tongji.chat.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatRoomService chatRoomService;

    @PutMapping
    public int createChatRoom() {
        try {
            return chatRoomService.createChatRoom();
        } catch (Exception e) {
            return -1;
        }
    }

    @PostMapping("/chat_room/{roomPort}/enter")
    public void addUser(@PathVariable("roomPort") int roomPort, @RequestParam String userName) {
        chatRoomService.addUser(roomPort, userName);
    }

    @PostMapping("/chat_room/{roomPort}/exit")
    public void exitChatRoom(@PathVariable("roomPort") int roomPort, @RequestParam String userName) {
        chatRoomService.removeUser(roomPort, userName);
    }

    @PostMapping("/chat_room/{roomPort}/{userName}/send")
    public void sendMessage(@PathVariable("roomPort") int roomPort, @PathVariable("userName") String userName, @RequestParam String content) {
        chatRoomService.sendMessage(roomPort, userName, content);
    }

    @PostMapping("/chat_room/{roomPort}")
    public void closeChatRoom(@PathVariable("roomPort") int roomPort) {
        chatRoomService.closeChatRoom(roomPort);
    }
}
