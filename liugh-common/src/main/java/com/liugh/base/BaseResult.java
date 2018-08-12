package com.liugh.base;

import lombok.Data;

/**
 * @author liugh
 * @since 2018-05-03
 */
@Data
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
    private Long elapsedMilliseconds = -1L;

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

}
