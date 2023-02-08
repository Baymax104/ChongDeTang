package com.cdtde.chongdetang.viewModel.index;

import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.repository.IndexRepository;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/8 2:52
 * @Version 1
 */
public class AppointViewModel extends ViewModel {
    private IndexRepository repo;

    public AppointViewModel() {
        repo = IndexRepository.getInstance();
    }

    public boolean isLogin() {
        return repo.getUser().getToken() != null;
    }
}
