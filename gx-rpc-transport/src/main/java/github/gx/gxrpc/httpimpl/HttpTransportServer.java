package github.gx.gxrpc.httpimpl;

import github.gx.gxrpc.RequestHandler;
import github.gx.gxrpc.TransportServer;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @program: gx-rpc
 * @description: 使用http协议的网络传输服务端
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-04 14:06
 **/
@Slf4j
public class HttpTransportServer implements TransportServer {

    private RequestHandler handler;
    private Server server;

    @Override
    public void init(int port, RequestHandler requestHandler) {
        this.handler = requestHandler;
        this.server = new Server(port);

        // servlet 接受请求
        // TODO 猜测是通过这个接口实现对端口的监听，等于说通过 jetty 进行了一层包装
        ServletContextHandler contextHandler = new ServletContextHandler();
        server.setHandler(contextHandler);
        // handler 的作用应该是操作进来的 http 访问请求
        // handler 是 jetty 再处理网络请求时的一个抽象
        // TODO 就是说内部类怎么可能不实例化就用到了
        ServletHolder holder = new ServletHolder(new RequestServlet());
        contextHandler.addServlet(holder, "/*");

    }

    @Override
    public void start() {
        try {
            server.start();
            // 为了延长 server 线程的时间，激活后做挂起操作
            server.join();
        } catch (Exception e) {
            // 不对异常做处理，使用日志记录异常后即结束
            log.error("启动 server 监听端口过程出现错误", e);
        }
    }

    @Override
    public void stop() {
        try{
            server.stop();
        } catch(Exception e) {
            log.error("关闭 server 过程中出现错误", e);
        }
    }

    class RequestServlet extends HttpServlet {

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            InputStream inputStream = req.getInputStream();
            OutputStream outputStream = resp.getOutputStream();
            // handler 是自定义的接口 为什么可以这样做
            if(handler != null) {
                handler.onRequest(inputStream, outputStream);
            }
            outputStream.flush();
        }
    }
}
