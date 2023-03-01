package com.cdtde.chongdetang.viewModel.my;

import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.repository.MyRepository;
import com.cdtde.chongdetang.util.ValidateUtil;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/2 0:17
 * @Version 1
 */
public class LoginViewModel extends ViewModel {
    private MyRepository repo;

    private String phone;
    private String password;

    public LoginViewModel() {
        repo = MyRepository.getInstance();
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean validate() {
        return ValidateUtil.validatePhone(phone) && ValidateUtil.validatePassword(password);
    }

    public void login() {
        repo.requestLogin(phone, password);
    }
}
