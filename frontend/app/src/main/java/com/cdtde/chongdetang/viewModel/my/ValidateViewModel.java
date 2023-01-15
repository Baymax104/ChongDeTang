package com.cdtde.chongdetang.viewModel.my;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.blankj.utilcode.util.MapUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;
import com.cdtde.chongdetang.entity.User;
import com.cdtde.chongdetang.repository.AppApplication;

import java.util.Map;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/15 17:43
 * @Version 1
 */
public abstract class ValidateViewModel extends ViewModel {
    private String code;

    public abstract User getUser();

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public boolean validate() {
        if (StringUtils.isEmpty(code)) {
            return false;
        }
        return code.equals("123");
    }

}
