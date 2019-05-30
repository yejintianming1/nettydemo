package netty.stringmessage.linebase;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class LineBaseClientHandler extends SimpleChannelInboundHandler {

    private static final Logger logger = Logger.getLogger(LineBaseClientHandler.class.getName());
    //计数器
    private static final AtomicInteger counter = new AtomicInteger(0);

    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取服务端返回的数据,并打印到控制台
        String content = (String) msg;
        System.out.println("received from server:" + content + " counter:" + counter.addAndGet(1));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warning("Unexpected exception from downstream :" + cause.getMessage());
        ctx.close();
    }
}
