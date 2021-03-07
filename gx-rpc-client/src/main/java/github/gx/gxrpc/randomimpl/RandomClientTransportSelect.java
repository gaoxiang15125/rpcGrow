package github.gx.gxrpc.randomimpl;

import github.gx.gxrpc.ClientTransportSelect;
import github.gx.gxrpc.TransportClient;
import github.gx.gxrpc.common.utils.ReflectionUtils;
import github.gxgeek.gxrpc.Peer;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @program: gx-rpc
 * @description: 随机客户端链接选择实现类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-07 13:27
 **/
@Slf4j
public class RandomClientTransportSelect implements ClientTransportSelect {

    // 简单的通过 synchronized 实现线程安全，要优化的地方多着呢 TODO

    private List<TransportClient> clients;

    public RandomClientTransportSelect() {
        clients = new ArrayList<>();
    }

    @Override
    public void init(List<Peer> peers, int count, Class<? extends TransportClient> clazz) {
        // count 是指每个端口对应多少个链接 ？？
        // 比较粗糙 服务没有鱼端口进行绑定 TODO
        // count 数量最少为 1
        count = Math.max(1, count);
        for(Peer peer:peers) {
            for(int i=0;i<count;i++) {
                TransportClient client = ReflectionUtils.newInstance(clazz);
                client.connect(peer);

                clients.add(client);
                log.info("connect succeed {}", peer);
            }
        }
    }

    @Override
    public synchronized TransportClient select() {
        // 很粗扫的负载均衡策略
        int randomInt = new Random().nextInt(clients.size());
        // TODO select 为 remove release 为 添加操作，完全不理解该类的做法了
        // 需要一本书
        return clients.remove(randomInt);
    }

    @Override
    public synchronized void release(TransportClient client) {
        // 释放占有的服务，将其返回到 list 中
        clients.add(client);
    }

    @Override
    public synchronized void close() {
        for(TransportClient client:clients) {
            client.close();
        }
        clients.clear();
    }
}
