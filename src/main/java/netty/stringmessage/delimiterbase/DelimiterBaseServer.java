package netty.stringmessage.delimiterbase;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class DelimiterBaseServer {

    private static final String delimiter_tag = "@#";

    public static void main(String[] args) {
        int port = 8080;
        new DelimiterBaseServer().bind(port);
    }

    private void bind(int port) {
        //创建两个EventLoopGroup实例
        //EventLoopGroup是包含一组专门用于处理网络事件的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建服务端辅助启动类ServerBootstrap对象
            ServerBootstrap b = new ServerBootstrap();
            //设置NIO线程组
            b.group(bossGroup,workerGroup)
                    //设置NioServerSocketChannel，对应与JDK NIO类ServerSocketChannel类
            .channel(NioServerSocketChannel.class)
                    //设置TCP参数，连接请求的最大队列长度
            .option(ChannelOption.SO_BACKLOG,1024)
                    //设置I/O事件处理类，用来处理消息的编解码以及我们的业务逻辑
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    //设置DelimiterBasedFrameDecoder处理器
                    ByteBuf delimiter = Unpooled.copiedBuffer(delimiter_tag.getBytes());
                    ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
                    //设置StringDecoder处理器
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new DelimiterBaseServerHandler());
                }
            });
            //绑定端口，同步等待成功
            ChannelFuture f  = b.bind(port).sync();
            //等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //优雅退出释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
