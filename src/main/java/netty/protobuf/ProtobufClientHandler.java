package netty.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ProtobufClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //channel建立之后，向服务端发送消息，需要注意的是这里写入的消息是完整的UserInfo.User对象
        //因为后续会被工具ProtobufVarint32LengthFieldPrepender与ProtobufEncoder进行编码处理
        UserInfo.User user = UserInfo.User.newBuilder()
                .setName("kongxuan")
                .setUserId(10000)
                .setEmail("wuyangsheng@163.com")
                .setMobile("153****0976")
                .setRemark("remark info").build();

        ctx.writeAndFlush(user);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
