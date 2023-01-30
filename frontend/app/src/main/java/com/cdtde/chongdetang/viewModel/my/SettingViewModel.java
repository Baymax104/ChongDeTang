package com.cdtde.chongdetang.viewModel.my;

import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.entity.User;
import com.cdtde.chongdetang.repository.MyRepository;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/16 15:39
 * @Version 1
 */
public class SettingViewModel extends ViewModel {
    private MyRepository repository;

    public SettingViewModel() {
        repository = MyRepository.getInstance();
    }

    public User getUser() {
        return repository.getUser();
    }
}
