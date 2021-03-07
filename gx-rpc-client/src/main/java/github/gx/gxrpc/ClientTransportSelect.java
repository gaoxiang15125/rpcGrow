package github.gx.gxrpc;

import github.gxgeek.gxrpc.Peer;

import java.util.List;

/**
 * @program: gx-rpc
 * @description: 客户端负载均衡策略实现类，理论上应该给注册中心管理吧
 *  TODO 看了具体的实现后，我有些搞不懂这个接口到底定义了什么、做了什么事情了
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-06 16:54
 **/
public interface ClientTransportSelect {
    /**
     *  初始化选择策略实现
     * @param peers 端口描述类
     * @param count 可用服务数量
     * @param clazz 客户端实现类
     */
    void init(List<Peer> peers,int count, Class<? extends TransportClient> clazz);

    /**
     * 负载均衡策略实现方法，选择具体使用哪一个服务进行调用
     * @return 执行目标服务的 客户端
     */
    TransportClient select();

    /**
     * 释放所选链接
     * @param client 需要被释放的 客户端
     */
    void release(TransportClient client);

    /**
     * 关闭所有创建的服务链接
     */
    void close();
}
