package com.xxd.dsi.proxy;

import com.xxd.dsi.invoke.MethodInvoker;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Auther: 许晓东
 * @Date: 19-7-22 15:32
 * @Description:
 */
public class Invocation {

    private Class<?> type;
    private Map<Method, MethodInvoker> method2Invokes;

    public Invocation() {
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public Map<Method, MethodInvoker> getMethod2Invokes() {
        return method2Invokes;
    }

    public void setMethod2Invokes(Map<Method, MethodInvoker> method2Invokes) {
        this.method2Invokes = method2Invokes;
    }
}
