// 服务器端
const WebSocket = require('ws');
const port = parseInt(process.argv[2]);
const wss = new WebSocket.Server({ port: port });
console.log("Chatroom started on localhost:" + port);

let clientCount = 0;

wss.on('connection', function connection(ws, req) {
    const ip = req.socket.remoteAddress;
    clientCount++;
    console.log("New connection from: " + ip + ", Total client: " + clientCount);

    ws.on('message', function incoming(message) {
	const type = JSON.parse(message).type;
	
	if (type == 'PIC') {
	    console.log("Received image from " + ip);
	}
	else {
	    console.log("Received message from " + ip + ": " + message);
	}

        // 广播收到的消息给所有客户端
        wss.clients.forEach(function each(client) {
            if (client !== ws && client.readyState === WebSocket.OPEN) {
                client.send(message);
            }
        });
    });

    ws.on("close", function close() {
        clientCount--;
        console.log("Client disconnected, Remain client: " + clientCount);

        if (clientCount == 0) {
            wss.close(() => {
                console.log("Chatroom shutdown");
            });
        }
    })
});
