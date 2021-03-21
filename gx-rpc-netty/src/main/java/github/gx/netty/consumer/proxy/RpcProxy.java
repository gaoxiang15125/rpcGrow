package github.gx.netty.consumer.proxy;

import github.gx.netty.protocol.InvokerProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @program: gx-rpc
 * @description: Rpc 代理实现类，通过代理模式实现
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-21 14:19
 **/
public class RpcProxy {

    // 有一点疑问，为什么 netty 声明流程与服务端不太一样
    private static String HOST_ADDRESS = "127.0.0.1";
    private static int PORT = 8081;
    // TODO 没有使用框架，所以没能方便的从配置文件加载内容

    public static void setTargetInfo(String host, int port) {
        HOST_ADDRESS = host;
        PORT = port;
    }

    public static <T> T create(Class<?> clazz) {
        //clazz传进来本身就是interface
        MethodProxy proxy = new MethodProxy(clazz);
        Class<?>[] interfaces = clazz.isInterface() ?
                new Class[]{clazz} :
                clazz.getInterfaces();
        T result = (T) Proxy.newProxyInstance(clazz.getClassLoader(), interfaces, proxy);
        return result;
    }

    /**
     * 方法代理类，用来指向服务端方法
     */
    private static class MethodProxy implements InvocationHandler {
        private Class<?> clazz;

        public MethodProxy(Class<?> clazz) {
            this.clazz = clazz;
        }

        /**
         * 代理的方式调用目标方法，Object 为具体对象调用本地方法，否则调用远程方法
         *
         * @param proxy
         * @param method
         * @param args
         * @return
         * @throws Throwable
         */
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //如果传进来是一个已实现的具体类（本次演示略过此逻辑)
            if (Object.class.equals(method.getDeclaringClass())) {
                try {
                    return method.invoke(this, args);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                //如果传进来的是一个接口（核心)
            } else {
                return rpcInvoke(proxy, method, args);
            }
            return null;
        }

        /**
         * 实现接口的核心方法
         *
         * @param method
         * @param args
         * @return
         */
        public Object rpcInvoke(Object proxy, Method method, Object[] args) {
            //传输协议封装
            InvokerProtocol msg = new InvokerProtocol();
            msg.setClassName(this.clazz.getName());
            msg.setMethodName(method.getName());
            msg.setValues(args);
            msg.setParams(method.getParameterTypes());

            final RpcProxyHandler consumerHandler = new RpcProxyHandler();
            EventLoopGroup group = new NioEventLoopGroup();
            try {
                Bootstrap b = new Bootstrap();
                b.group(group)
                        .channel(NioSocketChannel.class)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            public void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline pipeline = ch.pipeline();
                                //自定义协议解码器
                                /** 入参有5个，分别解释如下
                                 maxFrameLength：框架的最大长度。如果帧的长度大于此值，则将抛出TooLongFrameException。
                                 lengthFieldOffset：长度字段的偏移量：即对应的长度字段在整个消息数据中得位置
                                 lengthFieldLength：长度字段的长度：如：长度字段是int型表示，那么这个值就是4（long型就是8）
                                 lengthAdjustment：要添加到长度字段值的补偿值
                                 initialBytesToStrip：从解码帧中去除的第一个字节数
                                 */
//                                pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
//                                //自定义协议编码器
//                                pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                                //对象参数类型编码器
                                pipeline.addLast("encoder", new ObjectEncoder());
                                //对象参数类型解码器
                                pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                                pipeline.addLast("handler", consumerHandler);
                            }
                        });
                /**
                 * TODO 服务端与客户端所做的事情应该是相同的吧，为什么这里设置的不太一样
                 */
                ChannelFuture future = b.connect(HOST_ADDRESS, PORT).sync();
                future.channel().writeAndFlush(msg).sync();
                future.channel().closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 为什么只创建了一个线程池，服务端创建了两个
                group.shutdownGracefully();
            }
            return consumerHandler.getResponse();
        }

    }
}
