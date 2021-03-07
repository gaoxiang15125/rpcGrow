package github.gx.gxrpc.server;

import github.gx.gxrpc.common.utils.ReflectionUtils;
import github.gxgeek.gxrpc.Request;

/**
 * @program: gx-rpc
 * @description: 使用 java 代理模式，实现根据类、方法完成调用
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-04 20:42
 **/
public class ServiceInvoker {

    /**
     * 通过反射和传递的参数，实现远程调用目标方法
     * @param serviceInstance
     * @param request
     * @return
     */
    public Object invoke(ServiceInstance serviceInstance, Request request) {
        return ReflectionUtils.invoke(
                serviceInstance.getTarget(),
                serviceInstance.getMethod(),
                request.getParameters()
        );
    }
}
