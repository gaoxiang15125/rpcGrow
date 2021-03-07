package github.gx.gxrpc.example;

import github.gx.gxrpc.server.RpcServer;
import github.gx.gxrpc.server.RpcServerConfig;

/**
 * @program: gx-rpc
 * @description: 服务器启动样例类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-07 17:09
 **/
public class ServerExample {

    public static void main(String[] args) {
        RpcServer server = new RpcServer(new RpcServerConfig());
        // 注册服务
        TestImpl test = new TestImpl();
        server.register(TestInterface.class, test);

        // start 之后发生了阻塞，应该异步操作 ？？
        server.start();
    }
}
