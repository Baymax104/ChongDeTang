package com.cdtde.chongdetang.dataSource.web;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/22 18:43
 * @Version 1
 */
public class WebException extends Exception {

    private boolean isSuccess;

    public WebException(boolean isSuccess, String message) {
        super(message);
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
