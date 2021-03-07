package github.gx.gxrpc.server;

import github.gx.gxrpc.common.utils.ReflectionUtils;
import github.gxgeek.gxrpc.Request;
import github.gxgeek.gxrpc.ServiceDescriptor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: gx-rpc
 * @description: 管理rpc暴漏的服务
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-04 18:29
 **/
@Slf4j
public class ServiceManager {
    private Map<ServiceDescriptor, ServiceInstance> serverInstanceMap;

    public ServiceManager() {
        // 使用 线程安全的 hashMap 初始化我们的 服务注册表
        this.serverInstanceMap = new ConcurrentHashMap<>();
    }

    /**
     * 将目标接口的所有对外方法注册到我们的 map 中
     * @param interfaceClass 具体的接口定义
     * @param bean 接口的实现对象
     * 其实 在 spring Ioc 中已经写过一版的实现，这里重新再写一次而已
     */
    public <T>void register(Class<T> interfaceClass, T bean) {
        // 不同的服务通过什么办法调到我们的目标服务呢？ 难道是使用 开出端口提供调用的方式 ？？
        Method[] methods = ReflectionUtils.getPublicMethods(interfaceClass);
        for(Method method:methods) {
            ServiceDescriptor serviceDescriptor = ServiceDescriptor.parseFromClass(interfaceClass, method);
            ServiceInstance serviceInstance = new ServiceInstance(bean, method);
            // 也就是说当前为 类与实现 一对一 的关系 TODO
            serverInstanceMap.put(serviceDescriptor, serviceInstance);
            log.info("register service: {} {}",serviceDescriptor.getClazz(), serviceDescriptor.getMethod());
        }
        // 看起来是注册完成了
    }

    public ServiceInstance findServiceByRequest(Request request) {
        return serverInstanceMap.get(request.getServiceDescriptor());
    }
}
