package cn.edu.tongji;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	// 所有连接的 channels
	public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception { // (1)
		// 接收到 TextWebSocketFrame
		Channel incoming = ctx.channel();
		// 遍历所有连接的 channel 广播出去
		for (Channel channel : channels) {
			if (channel != incoming) {
				channel.writeAndFlush(new TextWebSocketFrame("[" + incoming.remoteAddress() + "]" + msg.text()));
			} else {
				channel.writeAndFlush(new TextWebSocketFrame("[you]" + msg.text()));
			}
		}
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception { // (2)
		// 有新的连接加入
		Channel incoming = ctx.channel();

		// 广播
		channels.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 加入"));

		channels.add(incoming);
		System.out.println("Client:" + incoming.remoteAddress() + "加入");
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception { // (3)
		// 连接断开时
		Channel incoming = ctx.channel();

		// 广播
		channels.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 离开"));

		System.out.println("Client:" + incoming.remoteAddress() + "离开");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
		// 连接被激活
		Channel incoming = ctx.channel();
		System.out.println("Client:" + incoming.remoteAddress() + "在线");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
		// 连接失活
		Channel incoming = ctx.channel();
		System.out.println("Client:" + incoming.remoteAddress() + "掉线");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) // (7)
			throws Exception {
		Channel incoming = ctx.channel();
		System.out.println("Client:" + incoming.remoteAddress() + "异常");
		// 当出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}

}