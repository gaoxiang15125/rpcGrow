package github.gxgeek.gxrpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @program: gx-rpc
 * @description: 表示一个可用服务
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-03 19:14
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDescriptor {
    /**
     * 对于本类，我们希望它可以指导系统检索到方法，且可以规定目标参数
     */
    private String clazz;
    private String method;
    private String returnType;
    private String[] parameterTypes;

    public static ServiceDescriptor parseFromClass(Class clazz, Method method) {
        ServiceDescriptor serviceDescriptor = new ServiceDescriptor();
        serviceDescriptor.setClazz(clazz.getName());
        serviceDescriptor.setMethod(method.getName());
        serviceDescriptor.setReturnType(method.getReturnType().getName());

        // 解析目标方法，将参数作为数组内容进行赋值
        Class[] parameterClasses = method.getParameterTypes();
        String[] parameterTypes = new String[parameterClasses.length];
        // 将参数类型与参数内容填入数组
        for(int i=0;i<parameterClasses.length;i++) {
            parameterTypes[i] = parameterClasses[i].getName();
        }
        serviceDescriptor.setParameterTypes(parameterTypes);
        return serviceDescriptor;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        // 通过 toString 方法简介判断两个对象是否相等
        String objStr = obj.toString();
        return this.toString().equals(objStr);
    }

    @Override
    public String toString() {
        return "clazz=" + clazz
                + ",method=" + method
                + ",returnType=" + returnType
                + ",parameterTypes=" + Arrays.toString(parameterTypes);
    }

    // TODO 一个疑问，为什么要重写这些 方法，正常的类本身不久支持hash检索吗
}
