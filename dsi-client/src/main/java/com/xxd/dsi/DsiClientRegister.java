package com.xxd.dsi;

import com.xxd.dsi.annotation.ServiceInfo;
import com.xxd.dsi.scanner.ClassPathInterfaceBeanDefinitionScanner;
import com.xxd.dsi.stub.DsiStubFactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Auther: 许晓东
 * @Date: 19-7-19 15:29
 * @Description: 注册service info bean.
 */
class DsiClientRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware, ResourceLoaderAware {
    private Environment environment;
    private ResourceLoader resourceLoader;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registerDisBeanDefinitions(importingClassMetadata, registry);
    }

    private void registerDisBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        ClassPathBeanDefinitionScanner scanner = new ClassPathInterfaceBeanDefinitionScanner(registry, false, environment, resourceLoader);

        Set<String> basePackages = getBasePackages(metadata);
        for (String basePackage : basePackages) {
            Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents(basePackage);
            for (BeanDefinition bd : beanDefinitions) {
                if (bd instanceof AnnotatedBeanDefinition) {
                    AnnotationMetadata annotationMetadata = ((AnnotatedBeanDefinition) bd).getMetadata();
                    Map<String, Object> attrs = annotationMetadata.getAnnotationAttributes(ServiceInfo.class.getCanonicalName());
                    registerDisStub(registry, annotationMetadata, attrs);
                }
            }
        }


    }

    private void registerDisStub(BeanDefinitionRegistry registry, AnnotationMetadata metadata, Map<String, Object> attrs) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DsiStubFactoryBean.class);
        String className = metadata.getClassName();
        builder.addPropertyValue("type", className);
        builder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        AbstractBeanDefinition definition = builder.getBeanDefinition();
        definition.setPrimary(true);
        BeanDefinitionHolder holder = new BeanDefinitionHolder(definition, className);
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    private Set<String> getBasePackages(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata
                .getAnnotationAttributes(EnableDsi.class.getCanonicalName());

        Set<String> basePackages = new HashSet<>();
        for (String pkg : (String[]) attributes.get("value")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        for (String pkg : (String[]) attributes.get("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        for (Class<?> clazz : (Class[]) attributes.get("basePackageClasses")) {
            basePackages.add(ClassUtils.getPackageName(clazz));
        }

        if (basePackages.isEmpty()) {
            basePackages.add(
                    ClassUtils.getPackageName(importingClassMetadata.getClassName()));
        }
        return basePackages;
    }
}
