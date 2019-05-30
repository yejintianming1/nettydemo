package netty.marshalling;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MarshallingSerializeClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //channel建立之后，向服务端发送消息，需要注意的是这里写入的消息是完整的UserInfo对象
        UserInfo user = UserInfo.newBuilder()
                .name("wuyangsheng")
                .userId(10000)
                .email("wuayangsheng@163.com")
                .mobile("182****7572")
                .remark("remark info").build();

        ctx.writeAndFlush(user);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
