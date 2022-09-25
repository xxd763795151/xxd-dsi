package com.xxd.dsi.scanner;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * @Auther: 许晓东
 * @Date: 19-7-19 16:12
 * @Description:
 */
public class ClassPathInterfaceBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    public ClassPathInterfaceBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters, Environment environment, ResourceLoader resourceLoader) {
        super(registry, useDefaultFilters, environment, resourceLoader);
        initialize();
    }

    private void initialize() {
        for (TypeFilter includeFilter : includeFilters()) {
            addIncludeFilter(includeFilter);
        }
    }

    protected Set<TypeFilter> includeFilters() {
        Set<TypeFilter> typeFilters = new HashSet<TypeFilter>(5);
        typeFilters.add(new AnnotationTypeFilter(Component.class));
        typeFilters.add(new AnnotationTypeFilter(Service.class));
        return typeFilters;
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata metadata = beanDefinition.getMetadata();
        if (metadata.isIndependent() && metadata.isInterface()) {
            if (metadata.getInterfaceNames().length == 1
                    && metadata.getInterfaceNames()[0].equals(Annotation.class.getName())) {
                // 姑且认为是个注解，即使不是注解，对于这种接口也不处理了
                return false;
            }
            return annotationConstraint(metadata);
        }

        return false;
    }

    protected boolean annotationConstraint(AnnotationMetadata metadata) {
        return true;
    }
}
