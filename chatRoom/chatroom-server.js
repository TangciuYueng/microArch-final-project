// 服务器端
const WebSocket = require('ws');
const wss = new WebSocket.Server({ port: 8080 });
console.log("Chatroom started on localhost:8080")

wss.on('connection', function connection(ws, req) {
    const ip = req.socket.remoteAddress;
    console.log("New connection from: " + ip);

    ws.on('message', function incoming(message) {
        console.log("Received message from " + ip + ": " + message);

        // 广播收到的消息给所有客户端
        wss.clients.forEach(function each(client) {
            if (client !== ws && client.readyState === WebSocket.OPEN) {
                client.send(message);
            }
        });
    });
});
