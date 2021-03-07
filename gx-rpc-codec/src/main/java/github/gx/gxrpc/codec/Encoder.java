package github.gx.gxrpc.codec;

/**
 * @program: gx-rpc
 * @description: 序列化接口定义类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-03 20:02
 **/
public interface Encoder {

    byte[] encode(Object obj);
}
