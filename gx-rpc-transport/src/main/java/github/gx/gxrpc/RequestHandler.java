package github.gx.gxrpc;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @program: gx-rpc
 * @description: 处理网络请求的 handler
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-03 21:32
 **/
public interface RequestHandler {

    void onRequest(InputStream inputStream, OutputStream outputStream);
}
