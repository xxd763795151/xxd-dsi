package com.xxd.dsi.annotation;

import com.xxd.dsi.beans.enums.ContentType;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * @Auther: 许晓东
 * @Date: 19-7-22 11:28
 * @Description: 声明式服务的注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceInfo {

    /**
     * 主机地址， ip:port
     */
    String host() default "";

    /**
     * 请求方法,get,post,delete,put
     */
    RequestMethod method() default RequestMethod.POST;

    /**
     * 请求url,e.g. /user/info
     */
    String url() default "";

    /**
     * 请求参数类型， e.g. application/json, form-data, form-urlencoded
     */
    ContentType type() default ContentType.APPLICATION_JSON;
}
