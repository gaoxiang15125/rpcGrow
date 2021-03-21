package github.gx.api.netty;

/**
 * @program: gx-rpc
 * @description: 需要rpc 调用的接口类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-20 15:40
 **/
public interface IRpcService {

    /**
     * 加
     */
    public int add(int a, int b);

    /**
     * 减
     */
    public int sub(int a, int b);

    /**
     * 乘
     */
    public int multiply(int a, int b);

    /**
     * 除
     */
    public int divide(int a, int b);
}
