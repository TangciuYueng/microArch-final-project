package cn.edu.tongji.chat.service;

import cn.edu.tongji.WebSocketChatServer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ChatRoomService {
    private final Map<Integer, WebSocketChatServer> chatRooms = new HashMap<>();
    private final Set<Integer> usedPorts = new HashSet<>();
    private static final int PORT_RANGE_START = 10000;
    private static final int PORT_RANGE_END = 10009;
    private ExecutorService executorService = Executors.newFixedThreadPool(10); // 创建一个固定大小的线程池
    private synchronized int allocatePort() {
        for (int port = PORT_RANGE_START; port <= PORT_RANGE_END; ++port) {
            // 端口没被占用
            if (!isPortOpen(port)) {
                usedPorts.add(port);
                return port;
            }
        }
        throw new RuntimeException("No available port");
    }

    private boolean isPortOpen(int port) {
        try (ServerSocket socket = new ServerSocket(port)) {
            return false;
        } catch (IOException e) {
            return true;
        }
    }

    public int createChatRoom() {
        int port = allocatePort();
        WebSocketChatServer server = new WebSocketChatServer(port);
        executorService.execute(() -> {
            try {
                server.run();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("room server exception");
            }
        });

        chatRooms.put(port, server);
        return port;
    }

    public void addUser(int roomPort, String userName) {
    }

    public void removeUser(int roomPort, String userName) {
    }

    public void sendMessage(int roomPort, String userName, String content) {
    }

    public void closeChatRoom(int roomPort) {
        var room = chatRooms.get(roomPort);
        room.shutdown();
        chatRooms.remove(roomPort);
        usedPorts.remove(roomPort);
    }
}
