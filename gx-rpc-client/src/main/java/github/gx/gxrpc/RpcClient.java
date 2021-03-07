package github.gx.gxrpc;

import github.gx.gxrpc.codec.Decoder;
import github.gx.gxrpc.codec.Encoder;
import github.gx.gxrpc.common.utils.ReflectionUtils;
import github.gx.gxrpc.config.RpcClientConfig;

import java.lang.reflect.Proxy;

/**
 * @program: gx-rpc
 * @description: rpc 客户端链接管理实例
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-07 15:31
 **/
public class RpcClient {

    private RpcClientConfig config;
    private Encoder encoder;
    private Decoder decoder;

    // 所有可用的链接信息 都交给 clientTransportSelect 对象进行处理
    private ClientTransportSelect clientTransportSelect;

    public RpcClient() {
        this(new RpcClientConfig());
    }

    public RpcClient(RpcClientConfig rpcClientConfig) {
        this.config = rpcClientConfig;
        this.encoder = ReflectionUtils.newInstance(config.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(config.getDecoderClass());
        this.clientTransportSelect = ReflectionUtils.newInstance(config.getSelectorClass());
        this.clientTransportSelect.init(config.getPeers(),
                config.getCount(),
                config.getTransportClient());
    }

    public <T>T getProxy(Class<T> clazz) {
        // 通过 JVM 动态代理获取指定 class 的代理对象
        // handler 中规定了 被代理类及其方法会通过什么样的方式被调用
        return (T) Proxy.newProxyInstance(
                // 这里应该是指定了创建代理类的路径
                getClass().getClassLoader(),
                new Class[]{clazz},
                new ClientRequestHandler(clazz, encoder, decoder, clientTransportSelect)
        );
    }
}
