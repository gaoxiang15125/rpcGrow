package github.gx.netty.registry;

import github.gx.netty.protocol.InvokerProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: gx-rpc
 * @description: 实现具体的服务注册、调用逻辑，猜测正常应该是给客户端提供一个地址，客户端再去链接服务端进行调用
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-21 11:53
 **/
public class RegistryHandler extends ChannelInboundHandlerAdapter {
    //用保存所有可用的服务
    public static ConcurrentHashMap<String, Object> registryMap = new ConcurrentHashMap<String,Object>();

    //保存所有相关的服务类
    private List<String> classNames = new ArrayList<String>();

    public RegistryHandler(){
        //完成递归扫描，好像在写 Ioc 控制反转的时候做过这些事
        // 扫描所有对外提供的方法
        scannerClass("github.gx.netty.provider");
        doRegister();
    }

    /**
     * 具体调用逻辑实现类，通过反射完成了 方法调用、参数传递
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Object result = new Object();
        InvokerProtocol request = (InvokerProtocol)msg;

        //当客户端建立连接时，需要从自定义协议中获取信息，拿到具体的服务和实参
        //使用反射调用
        if(registryMap.containsKey(request.getClassName())){
            Object clazz = registryMap.get(request.getClassName());
            Method method = clazz.getClass().getMethod(request.getMethodName(), request.getParams());
            result = method.invoke(clazz, request.getValues());
        }
        ctx.write(result);
        ctx.flush();
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /*
     * 递归扫描
     * TODO 尝试修改为 服务端自动注册，就像上一个版本做的
     */
    private void scannerClass(String packageName){
        URL url = this.getClass().getClassLoader().getResource(packageName.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            //如果是一个文件夹，继续递归
            if(file.isDirectory()){
                scannerClass(packageName + "." + file.getName());
            }else{
                classNames.add(packageName + "." + file.getName().replace(".class", "").trim());
            }
        }
    }

    /**
     * 完成注册
     */
    private void doRegister(){
        if(classNames.size() == 0){ return; }
        for (String className : classNames) {
            try {
                Class<?> clazz = Class.forName(className);
                Class<?> i = clazz.getInterfaces()[0];
                /**
                 * TODO 这个版本 一个接口只能对应一个实现
                 */
                // 修改了 需要获取构造方法后，在调用对应的初始化方法
                registryMap.put(i.getName(), clazz.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
