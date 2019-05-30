package netty.protobuf;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ProtobufServer {

    public static void main(String[] args) {
        int port = 8080;
        new ProtobufServer().bind(port);
    }

    private void bind(int port) {

        //配置服务端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    //设置NioServerSocketChannel,对应于JDK NIO类的ServerSocketChannel类
                    .channel(NioServerSocketChannel.class)
                    //设置TCP参数，客户端最大连接数
                    .option(ChannelOption.SO_BACKLOG, 100)
                    //设置日志级别
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //配置Protobuf解码工具ProtobufVarint32FrameDecoder与ProtobufDecoder
                            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            ch.pipeline().addLast(new ProtobufDecoder(UserInfo.User.getDefaultInstance()));
                            ch.pipeline().addLast(new ProtobufServerHandler());
                        }
                    });

            //绑定端口，同步等待成功
            ChannelFuture f  = b.bind(port).sync();
            //等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


}
