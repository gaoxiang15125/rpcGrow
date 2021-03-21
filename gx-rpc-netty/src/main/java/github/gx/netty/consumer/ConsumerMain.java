package github.gx.netty.consumer;

import github.gx.netty.api.IRpcService;
import github.gx.netty.consumer.proxy.RpcProxy;
import github.gx.netty.provider.RpcServiceImpl;

/**
 * @program: gx-rpc
 * @description: 客户端启动类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-21 13:14
 **/
public class ConsumerMain {

    public static void main(String[] args) {
        // 使用接口，通过代理获取远程对象，通过 netty 规则进行数据交互
        IRpcService rpcService = RpcProxy.create(IRpcService.class);

        RpcProxy.setTargetInfo("127.0.0.1", 8899);

        System.out.println(rpcService.add(1,2));
        System.out.println(rpcService.divide(5,2));
        System.out.println(rpcService.multiply(2, 5));
        System.out.println(rpcService.sub(10, 5));
    }
}
