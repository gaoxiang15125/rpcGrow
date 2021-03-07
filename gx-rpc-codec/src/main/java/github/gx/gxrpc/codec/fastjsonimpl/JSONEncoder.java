package github.gx.gxrpc.codec.fastjsonimpl;

import com.alibaba.fastjson.JSON;
import github.gx.gxrpc.codec.Encoder;

/**
 * @program: gx-rpc
 * @description: fastJson 序列化实现类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-03 20:07
 **/
public class JSONEncoder implements Encoder {
    @Override
    public byte[] encode(Object obj) {
        return JSON.toJSONBytes(obj);
    }
}
