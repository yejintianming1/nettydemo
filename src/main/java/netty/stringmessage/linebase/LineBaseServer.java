package netty.stringmessage.linebase;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class LineBaseServer {

    public static void main(String[] args) {
        int port = 8080;
        new LineBaseServer().bind(port);
    }

    private void bind(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建服务端辅助启动类ServerBootstrap对象
            ServerBootstrap b = new ServerBootstrap();
            //设置NIO线程组
            b.group(bossGroup,workerGroup)
                    //设置NIOServerSocketChannel,对应于JDK NIO类的ServerSocketChannel
            .channel(NioServerSocketChannel.class)
                    //设置TCP参数，连接请求的最大队列长度
            .option(ChannelOption.SO_BACKLOG,1024)
                    //设置I/O事件处理类，用来处理消息的编辑码以及我们的业务逻辑
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    //设置LineBaseFrameDecoder处理器
                    ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                    //设置StringDecoder处理器
                    ch.pipeline().addLast(new StringDecoder());
                    //设置收到客户端消息的具体业务处理器
                    ch.pipeline().addLast(new LineBaseServerHandler());
                }
            });

            //绑定端口，同步等待成功
            ChannelFuture f = b.bind(port).sync();
            //等待服务端监听端口关闭
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
