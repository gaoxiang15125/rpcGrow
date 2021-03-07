package github.gx.gxrpc.server;

import github.gx.gxrpc.RequestHandler;
import github.gx.gxrpc.TransportServer;
import github.gx.gxrpc.codec.Decoder;
import github.gx.gxrpc.codec.Encoder;
import github.gx.gxrpc.common.utils.ReflectionUtils;
import github.gxgeek.gxrpc.Request;
import github.gxgeek.gxrpc.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @program: gx-rpc
 * @description: Rpc 服务实现类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-04 21:23
 **/
@Slf4j
public class RpcServer {

    private RpcServerConfig config;
    private TransportServer transportServer;
    private Encoder encoder;
    private Decoder decoder;
    private ServiceManager serviceManager;
    private ServiceInvoker serviceInvoker;

    public RpcServer(RpcServerConfig rpcServerConfig) {
        this.config = rpcServerConfig;
        this.transportServer = ReflectionUtils.newInstance(
                config.getTransportClass());
        this.transportServer.init(config.getPort(), this.handler);

        // 为序列化对象赋值
        this.encoder = ReflectionUtils.newInstance(config.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(config.getDecoderClass());
        // 服务调用工具 承担注册中心职责 （这个模块可不可以拆分出去？）
        this.serviceManager = new ServiceManager();
        this.serviceInvoker = new ServiceInvoker();
    }

    /**
     * 将服务注册到 服务表
     * @param interfaceClass
     * @param bean
     * @param <T>
     */
    public <T>void register(Class<T> interfaceClass, T bean) {
        serviceManager.register(interfaceClass, bean);
    }

    /**
     * 
     */
    public void start() {
        this.transportServer.start();
    }

    public void stop() {
        this.transportServer.stop();
    }

    /**
     * 创建处理字节流的 handler
     * 将字节流解析后传递到正确的位置
     * 真正实现调用的方法，处理传送来的 request ，按照描述调用对应的方法，并将结果进行序列化后返回
     */
    private RequestHandler handler = new RequestHandler() {
        @Override
        public void onRequest(InputStream inputStream, OutputStream outputStream) {
            Response response = new Response();
            try {
                byte[] inBytes = IOUtils.readFully(inputStream, inputStream.available());
                // 获取 客户端调用的方法描述信息、参数信息
                Request request = decoder.decode(inBytes, Request.class);
                log.info("get request: {}", request);
                // 通过 jvm 提供的代理方法调用目标方法，并将结果用过 server 返回
                ServiceInstance targetService = serviceManager.findServiceByRequest(request);
                Object methodResult = serviceInvoker.invoke(targetService, request);

                // 根据调用结果构造 response ，序列化后作为结果返回
                response.setData(methodResult);
                response.setMessage("方法:" + request.toString() + " 访问成功");
                log.info("远程方法调用成功，调用结果为 {}", methodResult);
            } catch (IOException e) {
                log.error("远程调用服务过程出现错误", e);
                response.setCode(1);
                response.setMessage("Rpc 调用过程出现错误，错误信息为:" +
                        e.getClass().getName() + " "
                        + e.getMessage());
            } finally {
                // 将结果序列化后返回
                byte[] responseBytes = encoder.encode(response);
                try {
                    outputStream.write(responseBytes);
                    outputStream.flush();
                    log.info("远程方法执行结果写回输出流");
                } catch (IOException e) {
                    log.error("将方法结果写入输出流过程中出现错误", e);
                }
            }
        }
    };
}
