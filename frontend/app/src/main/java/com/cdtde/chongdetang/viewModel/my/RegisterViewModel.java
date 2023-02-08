package com.cdtde.chongdetang.viewModel.my;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.StringUtils;
import com.cdtde.chongdetang.entity.User;
import com.cdtde.chongdetang.repository.MyRepository;
import com.cdtde.chongdetang.util.ValidateUtil;
import com.cdtde.chongdetang.view.my.setting.RegisterFragment;
import com.cdtde.chongdetang.view.my.setting.ValidateFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/5 2:17
 * @Version 1
 */
public class RegisterViewModel extends ValidateViewModel {

    private MyRepository repo;

    private MutableLiveData<Integer> page;

    private List<Fragment> flowFragments;

    private String password;

    private String repeatPassword;

    private MutableLiveData<Boolean> phoneValidity;

    private MutableLiveData<Boolean> passwordValidity;

    private MutableLiveData<Boolean> repeatValidity;



    public RegisterViewModel() {
        repo = MyRepository.getInstance();
        page = new MutableLiveData<>(1);
        phoneValidity = new MutableLiveData<>(true);
        passwordValidity = new MutableLiveData<>(true);
        repeatValidity = new MutableLiveData<>(true);

        flowFragments = new ArrayList<>();
        flowFragments.add(RegisterFragment.newInstance());
        flowFragments.add(ValidateFragment.newInstance(getClass().getName()));

        phone = repo.getUser().getPhone();
    }

    public List<Fragment> getFlowFragments() {
        return flowFragments;
    }

    public void setPage(int page) {
        this.page.setValue(page);
    }

    public MutableLiveData<Integer> getPage() {
        return page;
    }

    public MutableLiveData<Boolean> getPhoneValidity() {
        return phoneValidity;
    }

    public MutableLiveData<Boolean> getPasswordValidity() {
        return passwordValidity;
    }

    public MutableLiveData<Boolean> getRepeatValidity() {
        return repeatValidity;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    @Override
    public User getUser() {
        return repo.getUser();
    }

    public boolean validateRegister() {
        boolean phoneValid = ValidateUtil.validatePhone(phone);
        phoneValidity.setValue(phoneValid);

        boolean passwordValid = ValidateUtil.validatePassword(password);
        passwordValidity.setValue(passwordValid);

        boolean repeatValid = StringUtils.equals(password, repeatPassword);
        repeatValidity.setValue(repeatValid);

        return phoneValid && passwordValid && repeatValid;
    }

    public void register() {
        repo.register(phone, password);
    }
}
