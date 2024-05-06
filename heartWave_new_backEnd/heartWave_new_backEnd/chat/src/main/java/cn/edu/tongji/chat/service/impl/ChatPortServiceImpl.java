package cn.edu.tongji.chat.service.impl;

import cn.edu.tongji.chat.dto.ChatResult;
import cn.edu.tongji.chat.dto.EnterChatRoomRequest;
import cn.edu.tongji.chat.service.ChatPortService;
import cn.edu.tongji.chat.service.RunJSService;
import jakarta.annotation.Resource;
import cn.edu.tongji.chat.model.ChatPort;
import org.springframework.stereotype.Service;
import cn.edu.tongji.chat.repository.ChatPortRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Service
public class ChatPortServiceImpl implements ChatPortService {
    @Resource
    private ChatPortRepository chatPortRepository;
    @Resource
    private ExecutorService exec;
    @Resource
    private RunJSService runJSService;

    @Override
    public ChatResult enterChatRoom(EnterChatRoomRequest enterChatRoomRequest) {
        try {
            ChatPort chatPort = chatPortRepository.findFirstByHostIdAndTypeAndGroupId(
                    enterChatRoomRequest.getRemoteId(),  //私聊时，根据对方id（群聊为null）
                    enterChatRoomRequest.getType(),
                    enterChatRoomRequest.getGroupId()    //群聊时，根据群id（私聊为null）
            );  //寻找port的host

            //为空，对方并未占有聊天端口，寻找一个空闲的端口，将其启动并返回信息
            if (chatPort == null) {
                final ChatPort newChatPort = chatPortRepository.findFirstByType(null);
                exec.execute(() -> runJSService.run("chatRoom/chatroom-server.js", newChatPort.getPort()));
                chatPortRepository.updateHostIdAndTypeAndGroupIdByPort(
                        newChatPort.getPort(),
                        enterChatRoomRequest.getSelfId(),
                        enterChatRoomRequest.getType(),
                        enterChatRoomRequest.getGroupId()
                );

                return new ChatResult(newChatPort.getPort(), true);
            }
            //否则直接返回对方已占用的端口号
            else {
                return new ChatResult(chatPort.getPort(), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ChatResult((short) -1, false);
        }
    }

    @Override
    public void defaultPort() {
        List<ChatPort> chatPorts = new ArrayList<>();

        for (int i = 30000; i < 31000; i++) {
            chatPorts.add(ChatPort.builder()
                    .port((short) i)
                    .hostId(null)
                    .type(null)
                    .groupId(null)
                    .build()
            );
        }

        chatPortRepository.saveAll(chatPorts);
    }
}
