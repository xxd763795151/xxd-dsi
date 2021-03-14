package com.xuxd.dsi.invoke;

import com.xuxd.dsi.beans.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 许晓东
 * @Date: 19-7-22 16:26
 * @Description:
 */
public class MethodInvoker implements Invoker<Response> {

    private Map<String, Object> attributes = new HashMap<>();

    @Override
    public Response invoke() {

        Response response = new Response();
        try {
            System.out.println("attrs: " + attributes);
            // TODO: 发起调用
            response.setResult("invoke success!");
        } catch (Exception e) {
            response.setThrowable(e);
        }

        return response;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}
