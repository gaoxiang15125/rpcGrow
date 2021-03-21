package github.gx.netty.api;

/**
 * 真正对外提供的接口方法
 */
public interface IRpcService {

	/** 加 */
	public int add(int a,int b);

	/** 减 */
	public int sub(int a,int b);

	/** 乘 */
	public int multiply(int a, int b);

	/** 除 */
	public int divide(int a, int b);

}
