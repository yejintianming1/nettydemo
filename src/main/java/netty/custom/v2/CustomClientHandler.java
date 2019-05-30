package netty.custom.v2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class CustomClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10000; i++) {
            //channel建立之后，向服务器发送消息，需要注意写入的消息是完整的UserInfo对象
            UserInfo user = UserInfo.newBuilder()
                    .name("伍羊生")
                    .userId(10000)
                    .email("wuyangsheng@163.com")
                    .mobile("153****0976")
                    .remark("remark info").build();

            ctx.writeAndFlush(user);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
