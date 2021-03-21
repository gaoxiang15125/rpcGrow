package github.gx.netty.api;

/**
 * 对外声明的接口，包含提供的服务与调用参数
 * 心跳监测
 */
public interface IRpcHelloService {
    String hello(String name);  
}  
