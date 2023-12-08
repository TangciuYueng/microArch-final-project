const WebSocket = require("ws")

const server = new WebSocket.Server({port: 8890})

server.on('open', () => {
    console.log('connected')
})

server.on('close', () => {
    console.log('disconnected')
})

server.on('connection', (ws, req) => {
    const ip = req.connection.remoteAddress
    const port = req.connection.remotePort
    const clientName = ip + port

    console.log('%s is connected', clientName)

    ws.send("Welcome " + clientName)

    ws.on('message', (message) => {
        console.log('received: %s from %s', message, clientName)

        server.clients.forEach(client => {
            if (client.readyState === WebSocket.OPEN) {
                client.send(clientName + " -> " + message)
            }
        })
    })
})