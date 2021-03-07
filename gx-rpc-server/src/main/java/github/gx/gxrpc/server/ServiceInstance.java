package github.gx.gxrpc.server;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * @program: gx-rpc
 * @description: 具体服务描述类 该类指向了 一个对象与其持有的方法，而不单单是描述信息
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-04 18:23
 **/
@Data
@AllArgsConstructor
public class ServiceInstance {

    private Object target;
    private Method method;
}
