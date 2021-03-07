package github.gxgeek.gxrpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: gx-rpc
 * @description: 表示 RPC 的返回
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-03 19:23
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            Response {
    /**
     * 服务返回编码 0 成功 非0失败
     * 写的比较粗糙 真正应该使用 枚举
     */
    private int code = 0;
    private String message = "ok";
    private Object data;

}
