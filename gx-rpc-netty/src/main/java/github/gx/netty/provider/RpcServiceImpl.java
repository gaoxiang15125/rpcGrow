package github.gx.netty.provider;

import github.gx.netty.api.IRpcService;

/**
 * @program: gx-rpc
 * @description: rpc 具体运算逻辑实现类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-21 11:47
 **/
public class RpcServiceImpl implements IRpcService {
    @Override
    public int add(int a, int b) {
        return a+b;
    }

    @Override
    public int sub(int a, int b) {
        return a-b;
    }

    @Override
    public int multiply(int a, int b) {
        return a * b;
    }

    @Override
    public int divide(int a, int b) {
        return b==0?0:a/b;
    }
}
