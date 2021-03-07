package github.gxgeek.gxrpc;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @program: gx-rpc
 * @description: 表示网络传输的一个端点，即记录一个链接目标
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-03 19:12
 **/
@Data
@AllArgsConstructor
public class Peer {

    private String host;
    private int port;
}
