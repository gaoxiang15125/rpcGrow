package github.gx.gxrpc.common.utils;

/**
 * @program: gx-rpc
 * @description: 测试类，用来验证反射方法是否正常
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-03 19:50
 **/
public class TestClass {

    private String infos = "有趣的名称";

    public String getInfos() {
        return infos;
    }

    public static String getTargetInfos() {
        return "静态的方法";
    }

    public String getInputStr(String str) {
        return str;
    }
}
