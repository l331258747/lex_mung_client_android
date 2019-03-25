package cn.lex_mung.client_android.mvp.model.entity;

import cn.lex_mung.client_android.mvp.model.api.Api;

import java.io.Serializable;

/**
 * 服务器返回数据基类
 */
public class BaseResponse<T> implements Serializable {
    private T data;
    private int code;
    private String message;
    private long time;

    public long getTime() {
        return time;
    }

    public T getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 请求是否成功
     *
     * @return true成功/false失败
     */
    public boolean isSuccess() {
        return code == Api.RequestSuccess;
    }
}
