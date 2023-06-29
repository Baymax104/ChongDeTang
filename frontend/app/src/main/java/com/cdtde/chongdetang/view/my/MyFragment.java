package com.cdtde.chongdetang.view.my;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseFragment;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.FragmentMyBinding;
import com.cdtde.chongdetang.repository.UserStore;
import com.cdtde.chongdetang.utils.Starter;
import com.cdtde.chongdetang.utils.WindowUtil;
import com.cdtde.chongdetang.view.my.address.AddressActivity;
import com.cdtde.chongdetang.view.my.appoint.UserAppointActivity;
import com.cdtde.chongdetang.view.my.collect.UserCollectActivity;
import com.cdtde.chongdetang.view.my.login.LoginActivity;
import com.cdtde.chongdetang.view.my.login.LogoutDialog;
import com.cdtde.chongdetang.view.my.setting.SettingActivity;
import com.cdtde.chongdetang.view.my.setting.userInfo.UserInfoActivity;
import com.cdtde.chongdetang.view.shop.ShoppingActivity;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/21 22:05
 * @Version 1
 */
public class MyFragment extends BaseFragment<FragmentMyBinding> {

    @InjectScope(Scopes.FRAGMENT)
    private States states;

    @InjectScope(Scopes.APPLICATION)
    private LogoutDialog.Messenger messenger;

    @InjectScope(Scopes.APPLICATION)
    private UserInfoActivity.Messenger infoMessenger;

    public static class States extends StateHolder {
        public State<String> userPhoto = new State<>(UserStore.getPhoto());
        public State<String> username = new State<>(UserStore.getUsername());
    }

    @Override
    protected ViewConfig configBinding() {
        return new ViewConfig(R.layout.fragment_my)
                .add(BR.state, states)
                .add(BR.handler, new Handler());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        messenger.logout.observeSend(getViewLifecycleOwner(), value -> UserStore.initUser());


        UserStore.getUserLoginEvent().observeSend(getViewLifecycleOwner(), value -> {
            states.username.setValue(UserStore.getUsername());
            states.userPhoto.setValue(UserStore.getPhoto());
        });


        infoMessenger.userInfoUpdate.observeSend(getViewLifecycleOwner(), value -> {
            states.username.setValue(UserStore.getUsername());
            states.userPhoto.setValue(UserStore.getPhoto());
        });

    }

    public class Handler {

        public final OnMenuItemClickListener onMenuItemClick = item -> {
            int id = item.getItemId();
            if (id == R.id.my_settings) {
                Starter.actionStart(activity, SettingActivity.class);
            }
            return true;
        };

        public final OnClickListener entryListener = v -> {
            if (UserStore.isLogin()) {
                if (v.getId() == R.id.user_icon) {
                    Starter.actionStart(activity, UserInfoActivity.class);
                } else if (v.getId() == R.id.appointment_entry) {
                    Starter.actionStart(activity, UserAppointActivity.class);
                } else if (v.getId() == R.id.collection_entry) {
                    Starter.actionStart(activity, UserCollectActivity.class);
                } else if (v.getId() == R.id.shopping_entry) {
                    Starter.actionStart(activity, ShoppingActivity.class);
                } else if (v.getId() == R.id.order_entry) {
                    ToastUtils.showShort("进入订单");
                } else if (v.getId() == R.id.address_entry) {
                    Starter.actionStart(activity, AddressActivity.class);
                } else if (v.getId() == R.id.notice_entry) {
                    ToastUtils.showShort("进入通知");
                } else if (v.getId() == R.id.feedback_entry) {
                    Starter.actionStart(activity, FeedbackActivity.class);
                }
            } else {
                Starter.actionStart(activity, LoginActivity.class);
            }
        };

    }

    @Override
    protected void initUIComponent(@NonNull FragmentMyBinding binding) {
        binding.toolbar.inflateMenu(R.menu.my_toolbar);
    }

}
