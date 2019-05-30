package netty.stringmessage.fixedlength;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class FixedLengthServer {

    public static void main(String[] args) {
        int port = 8080;
        new FixedLengthServer().bind(port);
    }

    private void bind(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建服务端辅助启动类ServerBootstrap对象
            ServerBootstrap b = new ServerBootstrap();
            //设置NIO线程组
            b.group(bossGroup, workerGroup)
                    //设置NioServerSocketChannel，对应JDK NIO类的ServerSocketChannel类
            .channel(NioServerSocketChannel.class)
                    //设置I/O事件处理类，用来处理消息的编解码以及我们的业务逻辑
            .option(ChannelOption.SO_BACKLOG,1024)
                    //设置I/O事件处理类，用来处理消息的编解码以及我们的业务逻辑
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    //设置FixedLengthFrameDecoder处理器，13为所需要传输的字符串，"你好,Netty!"的字节数组长度
                    ch.pipeline().addLast(new FixedLengthFrameDecoder(13));
                    //设置StringDecoder处理器
                    ch.pipeline().addLast(new StringDecoder());
                    //设置具体的业务逻辑处理类
                    ch.pipeline().addLast(new FixedLengthServerHandler());
                }
            });
            // 绑定端口，同步等待成功
            ChannelFuture f = b.bind(port).sync();
            // 等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 优雅退出释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
