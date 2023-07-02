package com.cdtde.chongdetang.useCase;

import com.blankj.utilcode.util.StringUtils;
import com.cdtde.chongdetang.base.vm.MessageHolder;
import com.cdtde.chongdetang.repository.UserStore;
import com.cdtde.chongdetang.utils.ValidateUtil;

import kotlin.Unit;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/14 23:55
 * @Version 1
 */
public class PasswordValidateUseCase extends MessageHolder {

    private String oldPassword;
    private String newPassword;
    private String repeatPassword;

    public final Event<Unit, String> validateEvent = new Event<>();

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public String validatePassword() {
        String userPwd = UserStore.getPassword();
        if (StringUtils.isEmpty(newPassword) ||
                StringUtils.isEmpty(repeatPassword)) {
            return "输入不能为空！";
        }
        // user.password可能为null
        // user.password == null时，不需要判断oldPassword，设为null
        if (userPwd == null) {
            oldPassword = null;
        }
        if (!StringUtils.equals(newPassword, repeatPassword)) {
            return "重复输入不一致！";
        }

        boolean isValid = ValidateUtil.validatePassword(newPassword);
        if (!isValid) {
            return "密码格式错误！";
        }

        // 验证通过
        return "OK";
    }

}
