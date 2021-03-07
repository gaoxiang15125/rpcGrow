package github.gx.gxrpc;

import github.gxgeek.gxrpc.Peer;

import java.io.InputStream;

/**
 * @program: gx-rpc
 * @description: 传输层客户端 相关方法接口类 用于简历链接
 * 1. 创建链接
 * 2. 发送数据、等待响应
 * 3. 关闭链接
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-03 21:21
 **/
public interface TransportClient {
    /**
     * 链接端点, 真正的 rpc 应该是使用长链接或者是别的什么方式吧
     * @param peer
     */
    void connect(Peer peer);

    /**
     * 写入输入流
     * @param data 需要写入的数据输入流
     */
    InputStream write(InputStream data);

    /**
     * 关闭链接 tcp 链接
     */
    void close();

    // TODO 第一版本使用 http 实现，之后修改为 Netty 框架实现
}
