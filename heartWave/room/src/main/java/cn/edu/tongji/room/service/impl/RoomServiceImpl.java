package cn.edu.tongji.room.service.impl;

import cn.edu.tongji.room.client.MusicRoomClient;
import cn.edu.tongji.room.dto.CloseRequest;
import cn.edu.tongji.room.dto.MusicRoomDTO;
import cn.edu.tongji.room.service.RoomService;
import cn.edu.tongji.room.util.WebSocketChatServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private MusicRoomClient client;

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
    @Override
    @Transactional
    public int createRoom(MusicRoomDTO musicRoomDTO) {
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
        System.out.println(musicRoomDTO);
        client.createMusicRoom(musicRoomDTO);
        return port;
    }

    @Override
    public void closeChatRoom(CloseRequest request) {
        int roomPort = request.getPort();
        int musicRoomId = request.getMusicRoomId();
        int operatorId = request.getOperatorId();

        var room = chatRooms.get(roomPort);
        room.shutdown();
        chatRooms.remove(roomPort);
        usedPorts.remove(roomPort);

        client.closeMusicRoom(musicRoomId, operatorId);
    }
}
