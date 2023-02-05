package com.cdtde.chongdetang.view.my.setting;

import android.content.Context;

import androidx.annotation.NonNull;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.DialogLogoutBinding;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.core.CenterPopupView;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/15 20:48
 * @Version 1
 */
public class LogoutDialog extends CenterPopupView {

    public LogoutDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_logout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        DialogLogoutBinding binding = DialogLogoutBinding.bind(getPopupImplView());
        binding.setLifecycleOwner(this);

        binding.cancel.setOnClickListener(v -> dismiss());

        binding.confirm.setOnClickListener(v -> {
            LiveEventBus.get("LogoutDialog-logout", Boolean.class).post(true);
            dismiss();
        });
    }
}
