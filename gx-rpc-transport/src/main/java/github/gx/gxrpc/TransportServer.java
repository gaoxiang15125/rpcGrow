package github.gx.gxrpc;

import github.gxgeek.gxrpc.Peer;

/**
 * @program: gx-rpc
 * @description: 服务端传输层相关接口 用于建立链接，看起来很像是 Tcp ack Fin
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-03 21:30
 **/
public interface TransportServer {

    /**
     * 初始化链接
     * 对于服务端，不需要知道自己的 host
     * 只需要绑定端口，获取绑定端口的实现类即可
     */
    void init(int port, RequestHandler requestHandler);

    /**
     * 启动监听
     */
    void start();

    /**
     * 关闭监听 todo 这里不对关闭结果进行验证 真的好吗
     */
    void stop();
}
