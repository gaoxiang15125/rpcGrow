package github.gx.netty.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: gx-rpc
 * @description: 服务协议类，用来定位服务位置与对应参数
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-21 12:39
 **/
@Data
public class InvokerProtocol implements Serializable {

    // 类名、方法名、参数列表、参数内容
    private String className;

    private String methodName;

    private Class<?>[] params;

    private Object[] values;
}
