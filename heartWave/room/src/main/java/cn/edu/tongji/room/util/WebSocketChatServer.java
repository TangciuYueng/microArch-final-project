package cn.edu.tongji.room.util;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class WebSocketChatServer {

    private int port;
    private ChannelFuture serverChannel;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public WebSocketChatServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {

        // 创建处理客户端连接的事件循环组 这里创建包含一个线程的事件循环组
        bossGroup = new NioEventLoopGroup(1);
        // 创建用于处理与客户端建立连接后的数据读写的工作事件循环组 通常多个线程
        workerGroup = new NioEventLoopGroup();
        try {
            // 创建服务器引导类
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    // 指定使用 NIO 进行网络通信
                    .channel(NioServerSocketChannel.class)
                    // 处理客户端连接的子处理器
                    .childHandler(new WebSocketChatServerInitializer())
                    // 设置 TCP 连接的最大等待队列长度
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 检测连接是否存活
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            System.out.println("WebsocketChatServer 启动于" + port);

            // 绑定端口，开始接收进来的连接
            serverChannel = b.bind(port).sync();

            // 等待服务器 socket 关闭
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            serverChannel.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            
    		System.out.println("WebsocketChatServer 关闭了" + port);
        }
    }

    public void shutdown() {
        if (serverChannel != null) {
            serverChannel.channel().close().syncUninterruptibly();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully().syncUninterruptibly();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully().syncUninterruptibly();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8890;
        }
        new WebSocketChatServer(port).run();

    }
}