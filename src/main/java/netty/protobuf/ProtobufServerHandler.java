package netty.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ProtobufServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 接收客户端发送过来的消息，其中经过前面解码工具ProtobufVarint32FrameDecoder与ProtobufDecoder
        //的处理，将字节码消息自动转换成了UserInfo.User对象
        UserInfo.User req = (UserInfo.User) msg;
        System.out.println("received from client:" + req.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
