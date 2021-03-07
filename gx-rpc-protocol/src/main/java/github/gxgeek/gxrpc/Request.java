package github.gxgeek.gxrpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: gx-rpc
 * @description: 表示 RPC 的一个请求
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-03 19:21
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    private ServiceDescriptor serviceDescriptor;
    private Object[] parameters;
}
