package github.gx.gxrpc.config;

import github.gx.gxrpc.ClientTransportSelect;
import github.gx.gxrpc.TransportClient;
import github.gx.gxrpc.codec.Decoder;
import github.gx.gxrpc.codec.Encoder;
import github.gx.gxrpc.codec.fastjsonimpl.JSONDecoder;
import github.gx.gxrpc.codec.fastjsonimpl.JSONEncoder;
import github.gx.gxrpc.common.define.SystemDefine;
import github.gx.gxrpc.randomimpl.RandomClientTransportSelect;
import github.gxgeek.gxrpc.Peer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: gx-rpc
 * @description: 客户端配置实现类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-07 13:47
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RpcClientConfig {
    /**
     * 这种写法还是第一次，为什么不直接使用接口类
     * 客户端链接对象
     * 序列化反序列化工具
     * 负载均衡选项器
     * 服务实现数量
     * 端口信息列表
     */
    private Class<? extends TransportClient> transportClient;
    private Class<? extends Decoder> decoderClass = JSONDecoder.class;
    private Class<? extends Encoder> encoderClass = JSONEncoder.class;
    private Class<? extends ClientTransportSelect> selectorClass = RandomClientTransportSelect.class;
    private int count;
    private List<Peer> peers = Arrays.asList(new Peer(SystemDefine.LOCAL_HOST, SystemDefine.DEFINE_PORT));
}
