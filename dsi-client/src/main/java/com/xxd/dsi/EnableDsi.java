package com.xxd.dsi;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Auther: 许晓东
 * @Date: 19-7-18 09:30
 * @Description: 启用声明式服务调用能力支持
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(DsiClientRegister.class)
public @interface EnableDsi {

    String[] value() default {};
    String[] basePackages() default {};
    Class<?>[] basePackageClasses() default {};
}
