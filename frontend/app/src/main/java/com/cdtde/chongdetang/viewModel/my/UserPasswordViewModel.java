package com.cdtde.chongdetang.viewModel.my;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdtde.chongdetang.entity.User;
import com.cdtde.chongdetang.repository.MyRepository;
import com.cdtde.chongdetang.util.ValidateUtil;
import com.cdtde.chongdetang.view.my.setting.userPassword.UserPasswordFragment;
import com.cdtde.chongdetang.view.my.setting.ValidateFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/14 23:55
 * @Version 1
 */
public class UserPasswordViewModel extends ValidateViewModel {
    private MyRepository repository;

    private MutableLiveData<Integer> page;

    private List<Fragment> flowFragments;

    private String oldPassword;
    private String newPassword;
    private String repeatPassword;


    public UserPasswordViewModel() {
        repository = MyRepository.getInstance();
        page = new MutableLiveData<>(1);

        flowFragments = new ArrayList<>();
        flowFragments.add(ValidateFragment.newInstance(getClass().getName()));
        flowFragments.add(UserPasswordFragment.newInstance());

        phone = repository.getUser().getPhone();
    }

    public User getUser() {
        return repository.getUser();
    }

    public MutableLiveData<Integer> getPage() {
        return page;
    }

    public List<Fragment> getFlowFragments() {
        return flowFragments;
    }

    public void setPage(int page) {
        this.page.setValue(page);
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String setUserPassword() {
        String msg = validatePassword();
        if ("OK".equals(msg)) {
            // TODO 上传后端修改
            repository.getUser().setPassword(EncryptUtils.encryptMD5ToString(newPassword));
        }
        return msg;
    }


    public String validatePassword() {
        User user = repository.getUser();
        if (StringUtils.isEmpty(newPassword) ||
            StringUtils.isEmpty(repeatPassword)) {
            return "输入不能为空！";
        }
        // user.password可能为null
        // user.password == null时，不需要判断oldPassword
        // TODO 需要上传服务器进行匹配
        if (user.getPassword() != null && !StringUtils.equals(user.getPassword(), oldPassword)) {
            return "输入与原密码不一致！";
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
