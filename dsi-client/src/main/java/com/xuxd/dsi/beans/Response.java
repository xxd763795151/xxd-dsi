package com.xuxd.dsi.beans;

/**
 * @Auther: 许晓东
 * @Date: 19-7-22 19:52
 * @Description:
 */
public class Response {

    private Throwable throwable;
    private String result;

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Response{" +
                "throwable=" + throwable +
                ", result='" + result + '\'' +
                '}';
    }
}
