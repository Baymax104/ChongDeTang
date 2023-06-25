package com.cdtde.chongdetang.viewModel.my;

import com.blankj.utilcode.util.StringUtils;
import com.cdtde.chongdetang.base.vm.MessageHolder;

import kotlin.Unit;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/15 19:32
 * @Version 1
 */
public class PhoneValidateUseCase extends MessageHolder {

    private String finalPhone;

    private String validCode;

    private String code;

    public final Event<Unit, Boolean> validateEvent = new Event<>();

    public void setFinalPhone(String finalPhone) {
        this.finalPhone = finalPhone;
    }

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFinalPhone() {
        return finalPhone;
    }

    public boolean validateCode() {
        if (StringUtils.isEmpty(code)) {
            return false;
        }

        // 测试专用，0000为测试码
        // ******************
        if (code.equals("0000")) {
            validCode = "0000";
        }
        //********************

        return code.equals(validCode);
    }


}
