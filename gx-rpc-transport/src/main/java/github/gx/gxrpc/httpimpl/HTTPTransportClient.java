package github.gx.gxrpc.httpimpl;

import github.gx.gxrpc.TransportClient;
import github.gxgeek.gxrpc.Peer;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @program: gx-rpc
 * @description: 使用 http 作为传输协议的版本
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-03 21:35
 **/
public class HTTPTransportClient implements TransportClient {
    /**
     * 使用 http 请求作为数据传输协议，缺点：效率低、使用不标准
     * 有点大材小用的感觉
     * 相关静态字符串
     */
    private static final String HTTP_HEAD = "http://";

    private String url;

    @Override
    public void connect(Peer peer) {
        this.url = HTTP_HEAD + peer.getHost() + ":" + peer.getPort();
    }

    @Override
    public InputStream write(InputStream data) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(this.url).openConnection();
            // 开启读写功能，不再对用户信息做缓存
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.connect();
            IOUtils.copy(data, httpURLConnection.getOutputStream());

            // 对返回码进行监测，以决定下一步执行什么操作
            // 所谓有始有终，对返回结果进行判断
            int resultCode = httpURLConnection.getResponseCode();
            if(resultCode == HttpURLConnection.HTTP_OK) {
                return httpURLConnection.getInputStream();
            } else {
                return httpURLConnection.getErrorStream();
            }
        }catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() {
        // 默认 httpConnection 为短链接，不需要进行 close 操作
    }
}
