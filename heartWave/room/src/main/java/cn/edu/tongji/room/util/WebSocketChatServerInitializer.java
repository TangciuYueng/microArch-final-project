package cn.edu.tongji.room.util;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketChatServerInitializer extends
		ChannelInitializer<SocketChannel> {

	@Override
    public void initChannel(SocketChannel ch) throws Exception {
		// 获取与客户端通信的 pipeline
		ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new HttpObjectAggregator(64*1024));
		// 支持异步写大数据流，不会占用过多的内存，防止发生内存溢出
		pipeline.addLast(new ChunkedWriteHandler());
		// 处理 HTTP 请求，将 WebSocket 升级请求转发到指定路径
		pipeline.addLast(new HttpRequestHandler("/ws"));
		// 处理 WebSocket 协议的握手和控制帧
		pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
		pipeline.addLast(new TextWebSocketFrameHandler());

    }
}
