package com.xxd.dsi.proxy;

import com.xxd.dsi.beans.Response;
import com.xxd.dsi.invoke.Invoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Auther: 许晓东
 * @Date: 19-7-22 15:21
 * @Description:
 */
public class DsiInvocationHandler implements InvocationHandler {

    private Invocation invocation;

    public DsiInvocationHandler(Invocation invocation) {
        this.invocation = invocation;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //TODO: 根据需要提供相关实现
        //System.out.println("proxy: " + proxy);
        System.out.println("method: " + method);
        System.out.println("args: " + Arrays.toString(args));
        Invoker<Response> invoker = invocation.getMethod2Invokes().get(method);

        return invoker.invoke();
        //return invoker.invoke(Arrays.toString(args));
    }
}
