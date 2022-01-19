package com.vietnam.lottery.common.utils;

import java.io.Serializable;

/**
 * @author Gyan
 * @since 2021/11/8
 */
public class ResultModel<T> implements Serializable {
    private static final long serialVersionUID = -199320501404298336L;

    /**
     * 响应码
     */
    private int code;
    /**
     * 响应数据
     */
    private T data;
    /**
     * 响应信息
     */
    private String msg;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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


    @Override
    public String toString() {
        return "ResultModel{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}