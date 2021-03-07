package github.gx.gxrpc.common.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: gx-rpc
 * @description: 反射实现工具类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-03 19:29
 **/
public class ReflectionUtils {

    /**
     * 根据 class 创建对象,方便之后对对象创建方式进行扩展
     * @param clazz 待创建对象的类
     * @param <T> 对象类型
     * @return 创建好的对象
     */
    public static <T> T newInstance(Class<T> clazz) {
        try{
            // 已经是废弃方法了，如何解决？？ TODO
            return clazz.newInstance();
        }catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 获取目标类的 共有方法
     * @param clazz 目标类
     * @return 类中的共有方法
     */
    public static Method[] getPublicMethods(Class clazz) {
        // 获取一个类的锁又方法 不包含父类方法
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> pMethods = new ArrayList<>();
        for(Method method:methods) {
            if(Modifier.isPublic(method.getModifiers())) {
                pMethods.add(method);
            }
        }
        return pMethods.toArray(new Method[0]);
    }

    /**
     * 调用指定对象的指定方法
     * @param obj 被调用方法的对象
     * @param method 被调用的方法
     * @param args 方法的参数
     * @return 返回结果
     * TODO 很明显 这里没有对参数 顺序、内容进行校验 健壮性很低
     */
    public static Object invoke(Object obj, Method method, Object... args) {
        try{
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
