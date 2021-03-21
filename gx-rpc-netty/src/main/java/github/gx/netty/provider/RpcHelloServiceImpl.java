package github.gx.netty.provider;

import github.gx.netty.api.IRpcHelloService;

/**
 * @program: gx-rpc
 * @description: 心跳监测服务实现类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-21 11:45
 **/
public class RpcHelloServiceImpl implements IRpcHelloService {
    @Override
    public String hello(String name) {
        // 理论上不应该返回字符串或者 类型，而应该使用 response 进行封装
        return "Hello " + name;
    }
}
