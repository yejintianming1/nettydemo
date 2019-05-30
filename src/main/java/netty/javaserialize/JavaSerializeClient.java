package netty.javaserialize;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class JavaSerializeClient {

    public static void main(String[] args) {
        int port = 8080;
        new JavaSerializeClient().connect(port, "127.0.0.1");
    }

    private void connect(int port, String host) {
        // 配置客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //配置java默认序列化解码工具ObjectEncoder
                            ch.pipeline().addLast(new ObjectEncoder());
                            ch.pipeline().addLast(new JavaSerializeClientHandler());
                        }
                    });

            ChannelFuture f  = b.connect(host,port).sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }
}
