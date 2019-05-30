package netty.marshalling;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

public class MarshallingCodeCFactory {
    // 首先通过Marshalling序列化工厂类，参数serial标识创建的是java序列化工厂对象
    private final static MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
    // 创建了MarshallingConfiguration对象，配置了版本号为5
    private static final MarshallingConfiguration configuration = new MarshallingConfiguration();

    static {
        configuration.setVersion(5);
    }

    /**
     * 创建解码器 MarshallingDecoder
     * @return
     */
    public static MarshallingDecoder buildMarshallingDecoder() {
        // 根据marshallerFactory和configuration创建provider
        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);
        // 构建Netty的MarshallingDecoder对象，两个参数
        return new MarshallingDecoder(provider, 1024 * 100);
    }

    /**
     * 创建编码器MarshallingEncoder
     */
    public static MarshallingEncoder buildMarshallingEncoder() {
        MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);
        // 构建Netty的MarshallingEncoder对象，MarshallingEncoder用于实现序列化接口的POJO对象序列化为二进制数组
        return new MarshallingEncoder(provider);
    }
}
