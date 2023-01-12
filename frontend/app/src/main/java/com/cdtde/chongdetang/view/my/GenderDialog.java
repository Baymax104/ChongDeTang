package com.cdtde.chongdetang.view.my;

import android.content.Context;

import androidx.annotation.NonNull;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.DialogUserGenderBinding;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.core.BottomPopupView;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/9 16:45
 * @Version 1
 */
public class GenderDialog extends BottomPopupView {

    public GenderDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_user_gender;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        DialogUserGenderBinding binding = DialogUserGenderBinding.bind(getPopupImplView());
        binding.setLifecycleOwner(this);

        binding.male.setOnClickListener(v -> {
            LiveEventBus.get("user_gender", String.class).post("男");
            dismiss();
        });

        binding.female.setOnClickListener(v -> {
            LiveEventBus.get("user_gender", String.class).post("女");
            dismiss();
        });

        binding.cancel.setOnClickListener(v -> dismiss());
    }
}
