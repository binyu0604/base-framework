package com.liugh.base;

/**
 * @author liugh
 * @since 2018-05-03
 */
public class BaseResult<T> {
    private String result;
    private String msg;
    private T data;
    /**
     * 是否处理成功
     */
    private boolean isSuccess;
    /**
     * 处理耗时(毫秒)
     */
    private Long elapsedMilliseconds;

    public BaseResult() {
    }

    public BaseResult(String result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public BaseResult(String result, String msg, T data) {
        this.result = result;
        this.msg = msg;
        this.data = data;
    }

    public BaseResult(String result, String msg, Long elapsedMilliseconds) {
        this.result = result;
        this.msg = msg;
        this.elapsedMilliseconds = elapsedMilliseconds;
    }

    public BaseResult(String result, String msg, T data, Long elapsedMilliseconds) {
        this.result = result;
        this.msg = msg;
        this.data = data;
        this.elapsedMilliseconds = elapsedMilliseconds;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getElapsedMilliseconds() {
        return elapsedMilliseconds;
    }

    public void setElapsedMilliseconds(long elapsedMilliseconds) {
        this.elapsedMilliseconds = elapsedMilliseconds;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
