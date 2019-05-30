package netty.stringmessage.linebase;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.atomic.AtomicInteger;

public class LineBaseServerHandler extends SimpleChannelInboundHandler {

    //字符串分隔符
    private static final String delimiter_tag = "@#";
    //计数器
    private static final AtomicInteger counter = new AtomicInteger(0);

    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接收客户端发送的字符串，并打印控制台
        String content = (String) msg;
        System.out.println("received from client:" + content + " counter:"+ counter.addAndGet(1));

        //将数据重新发送到客户端
        content += "\n";
        ByteBuf echo = Unpooled.copiedBuffer(content.getBytes());
        ctx.writeAndFlush(echo);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
