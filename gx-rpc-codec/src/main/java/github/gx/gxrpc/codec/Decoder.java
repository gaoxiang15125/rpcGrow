package github.gx.gxrpc.codec;

/**
 * @program: gx-rpc
 * @description: 反序列化接口定义
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-03 20:03
 **/
public interface Decoder {

    <T>T decode(byte[] bytes, Class<T> clazz);
}
