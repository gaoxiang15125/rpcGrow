package github.gx.gxrpc.example;

/**
 * @program: gx-rpc
 * @description: 测试接口实现类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-07 17:06
 **/
public class TestImpl implements TestInterface{
    // 跟实际的 web 项目还有很远的距离
    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int sub(int a, int b) {
        return a - b;
    }
}
