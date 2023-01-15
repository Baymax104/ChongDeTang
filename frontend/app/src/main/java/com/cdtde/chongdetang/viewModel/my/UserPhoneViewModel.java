package com.cdtde.chongdetang.viewModel.my;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.cdtde.chongdetang.entity.User;
import com.cdtde.chongdetang.repository.MyRepository;
import com.cdtde.chongdetang.view.my.setting.userPhone.UserPhoneFragment;
import com.cdtde.chongdetang.view.my.setting.ValidateFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/15 19:32
 * @Version 1
 */
public class UserPhoneViewModel extends ValidateViewModel {
    private MyRepository repository;

    private MutableLiveData<Integer> page;

    private List<Fragment> flowFragments;

    public String newPhone;


    public UserPhoneViewModel() {
        repository = MyRepository.getInstance();

        page = new MutableLiveData<>(1);

        flowFragments = new ArrayList<>();
        flowFragments.add(ValidateFragment.newInstance(getClass().getName()));
        flowFragments.add(UserPhoneFragment.newInstance());
    }

    @Override
    public User getUser() {
        return repository.getUser();
    }

    public void setPage(int page) {
        this.page.setValue(page);
    }

    public MutableLiveData<Integer> getPage() {
        return page;
    }

    public List<Fragment> getFlowFragments() {
        return flowFragments;
    }

    public void setNewPhone(String newPhone) {
        this.newPhone = newPhone;
    }
}
