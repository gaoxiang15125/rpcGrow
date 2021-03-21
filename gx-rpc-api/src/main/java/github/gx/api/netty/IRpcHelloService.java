package github.gx.api.netty;

/**
 * @program: gx-rpc
 * @description: 通过 rpc 进行简单的通信，监测服务是否可用
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-19 18:43
 **/
public interface IRpcHelloService {
    String hello(String userName);
}
