package com.cdtde.chongdetang.entity;

import androidx.annotation.NonNull;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/3 19:03
 * @Version 1
 */
public class Result<T> {
    private String status;
    private String message;
    private T data;

    public Result() {
    }

    public Result(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return "success".equals(status);
    }

    @NonNull
    @Override
    public String toString() {
        return "Result{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
