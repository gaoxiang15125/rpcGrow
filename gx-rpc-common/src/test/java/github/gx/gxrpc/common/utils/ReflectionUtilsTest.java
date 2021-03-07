package github.gx.gxrpc.common.utils;

import junit.framework.TestCase;

import java.lang.reflect.Method;

/**
 * @program: gx-rpc
 * @description:
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-03 19:49
 **/
public class ReflectionUtilsTest extends TestCase {

    public void testNewInstance() {
    }

    public void testGetPublicMethods() {
    }

    public void testInvoke() {
        // 全部流程都在这里执行
        TestClass testClass = ReflectionUtils.newInstance(TestClass.class);
        Method[] methods = ReflectionUtils.getPublicMethods(TestClass.class);
        String result = (String) ReflectionUtils.invoke(testClass, methods[0]);
        assertEquals(result, "静态的方法");
        result = (String) ReflectionUtils.invoke(testClass, methods[2], "okInfos");
        assertEquals(result, "okInfos");
        result = (String) ReflectionUtils.invoke(testClass, methods[1]);
        assertEquals(result, "有趣的名称");
    }
}