package netty.marshalling;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MarshallingSerializeServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接收客户端发送过来的消息，其中经过前面解码工具
        //的处理，将字节码消息自动转换成了UserInfo对象
        UserInfo req = (UserInfo) msg;
        System.out.println("received from client:" + req.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
         cause.printStackTrace();
         ctx.close();
    }
}
