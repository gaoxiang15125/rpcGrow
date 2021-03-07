package github.gx.gxrpc.codec.fastjsonimpl;

import com.alibaba.fastjson.JSON;
import github.gx.gxrpc.codec.Decoder;

/**
 * @program: gx-rpc
 * @description: fastJson 反序列化实现类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-03 20:05
 **/
public class JSONDecoder implements Decoder {


    @Override
    public <T> T decode(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }
}
