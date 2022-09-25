package com.xxd.dsi.stub;

import com.xxd.dsi.annotation.ServiceInfo;
import com.xxd.dsi.invoke.MethodInvoker;
import com.xxd.dsi.proxy.Invocation;
import com.xxd.dsi.proxy.InvocationHandlerFactory;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Auther: 许晓东
 * @Date: 19-7-22 14:29
 * @Description:
 */
public class DsiStub {


    private DsiStub() {
    }

    public static <T> T newInstance(Class<?> type) {
        Invocation invocation = createInvocation(type);
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, InvocationHandlerFactory.create(invocation));
    }


    private static Invocation createInvocation(Class<?> type) {
        Invocation invocation = new Invocation();
        // 保存类信息
        invocation.setType(type);
        // 保存注解信息
        Set<Method> methods = MethodIntrospector.selectMethods(type, new ReflectionUtils.MethodFilter() {
            @Override
            public boolean matches(Method method) {
                return AnnotationUtils.findAnnotation(method, ServiceInfo.class) != null;
            }
        });

        Map<Method, MethodInvoker> method2Invoker = new HashMap<>();
        for (Method method : methods) {
            ServiceInfo serviceInfo = AnnotationUtils.getAnnotation(method, ServiceInfo.class);
            Map<String, Object> attrs = AnnotationUtils.getAnnotationAttributes(serviceInfo);
            MethodInvoker invoker = new MethodInvoker();
            invoker.setAttributes(attrs);
            method2Invoker.put(method, invoker);
        }
        invocation.setMethod2Invokes(method2Invoker);
        return invocation;
    }
}
