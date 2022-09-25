package com.xxd.dsi.stub;

import org.springframework.beans.factory.FactoryBean;

/**
 * @Auther: 许晓东
 * @Date: 19-7-22 14:27
 * @Description:
 */
public class DsiStubFactoryBean implements FactoryBean<Object> {

    private Class<?> type;


    @Override
    public Object getObject() throws Exception {

        return DsiStub.newInstance(type);
    }

    @Override
    public Class<?> getObjectType() {
        return this.type;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}
