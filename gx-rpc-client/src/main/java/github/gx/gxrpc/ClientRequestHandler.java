package github.gx.gxrpc;

import github.gx.gxrpc.codec.Decoder;
import github.gx.gxrpc.codec.Encoder;
import github.gxgeek.gxrpc.Request;
import github.gxgeek.gxrpc.Response;
import github.gxgeek.gxrpc.ServiceDescriptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @program: gx-rpc
 * @description: 客户端通过代理实现远程方法调用的 实现类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-07 15:57
 **/
@Slf4j
public class ClientRequestHandler implements InvocationHandler {
    /**
     * handler 在这里是对某个接口的代理，而不仅仅服务于一个代理对象了
     * 通过代理方法实现 通过 http 协议向目标端口发送信息，并接受返回结果
     */
    private Class clazz;
    private Encoder encoder;
    private Decoder decoder;
    private ClientTransportSelect clientSelect;

    /**
     * 构造方法，代理目标类中的所有方法
     * @param clazz 目标接口
     * @param encoder 序列化工具
     * @param decoder 反序列化工具
     * @param clientTransportSelect 连接端口的实例对象
     */
    public ClientRequestHandler(Class clazz,
                                Encoder encoder,
                                Decoder decoder,
                                ClientTransportSelect clientTransportSelect) {
        this.clazz = clazz;
        this.encoder = encoder;
        this.decoder = decoder;
        this.clientSelect = clientTransportSelect;
    }

    /**
     *
     * @param proxy 猜测这里传递的是被代理的类的实例对象 TODO
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        /**
         * 通过编写的 解析方法获取 目标方法描述信息，构造 request后
         * 经由 client 传输并获取返回结果
         */
        ServiceDescriptor serviceDescriptor = ServiceDescriptor.parseFromClass(clazz, method);
        Request request = new Request();
        request.setParameters(args);
        request.setServiceDescriptor(serviceDescriptor);

        Response response = remoteInvoke(request);
        if(response == null || response.getCode() != 0) {
            throw new IllegalAccessError("调用远程方法过程出现错误: " +
                    request.toString() + "\n参数信息为: "
                    + Arrays.toString(args));
        }
        return response.getData();
    }

    private Response remoteInvoke(Request request) {
        TransportClient transportClient = null;
        Response response;
        try {
            transportClient = clientSelect.select();
            // 进行序列化操作
            byte[] encodeInfo = encoder.encode(request);
            InputStream inputStream = transportClient.write(new ByteArrayInputStream(encodeInfo));

            // 读出序列化后的 response
            byte[] resultByte = IOUtils.readFully(inputStream, inputStream.available());
            response = decoder.decode(resultByte, Response.class);
        } catch (Exception e) {
            log.error("远程调用方法过程中出现错误", e);
            response = new Response();
            response.setCode(1);
            response.setMessage(e.getMessage());
        } finally {
            clientSelect.release(transportClient);
        }
        return response;
    }
}
