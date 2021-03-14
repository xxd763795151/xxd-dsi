package com.xuxd.dsi.proxy;

import java.lang.reflect.InvocationHandler;

/**
 * @Auther: 许晓东
 * @Date: 19-7-22 15:31
 * @Description:
 */
public class InvocationHandlerFactory {
    private InvocationHandlerFactory() {

    }

    public static InvocationHandler create(Invocation invocation) {
        return new DsiInvocationHandler(invocation);
    }
}
