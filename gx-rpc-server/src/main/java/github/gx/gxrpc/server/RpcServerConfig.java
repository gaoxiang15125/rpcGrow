package github.gx.gxrpc.server;

import github.gx.gxrpc.TransportServer;
import github.gx.gxrpc.codec.Decoder;
import github.gx.gxrpc.codec.Encoder;
import github.gx.gxrpc.codec.fastjsonimpl.JSONDecoder;
import github.gx.gxrpc.codec.fastjsonimpl.JSONEncoder;
import github.gx.gxrpc.common.define.SystemDefine;
import github.gx.gxrpc.httpimpl.HttpTransportServer;
import lombok.Data;

/**
 * @program: gx-rpc
 * @description: rpc服务具体使用的实现配置类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-04 18:11
 **/
@Data
public class RpcServerConfig {
    // TODO 目前使用赋值的方式指定各个模块的具体实现方式，之后可以修改为工厂模式
    private Class<? extends TransportServer> transportClass = HttpTransportServer.class;
    private Class<? extends Encoder> encoderClass = JSONEncoder.class;
    private Class<? extends Decoder> decoderClass = JSONDecoder.class;
    private int port = SystemDefine.DEFINE_PORT;
}
