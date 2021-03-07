package github.gx.gxrpc.example;

import github.gx.gxrpc.RpcClient;
import github.gx.gxrpc.config.RpcClientConfig;
import github.gx.gxrpc.httpimpl.HTTPTransportClient;

/**
 * @program: gx-rpc
 * @description: 客户端启动样例类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-07 17:02
 **/
public class ClientExample {

    public static void main(String[] args) {
        RpcClientConfig clientExample = new RpcClientConfig();
        clientExample.setTransportClient(HTTPTransportClient.class);
        RpcClient client = new RpcClient(clientExample);
        TestInterface testInterface = client.getProxy(TestInterface.class);
        System.out.println(testInterface.add(1,2));
        System.out.println(testInterface.add(2,1));
    }
}
