package com.cdtde.chongdetang.view.my;

import android.content.Context;

import androidx.annotation.NonNull;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.DialogUserIconBinding;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.core.BottomPopupView;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/9 18:17
 * @Version 1
 */
public class IconDialog extends BottomPopupView {

    public IconDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_user_icon;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        DialogUserIconBinding binding = DialogUserIconBinding.bind(getPopupImplView());
        binding.setLifecycleOwner(this);

        binding.takePhoto.setOnClickListener(v -> {
            LiveEventBus.get("user_icon_action", Integer.class)
                    .post(1);
            dismiss();
        });

        binding.openAlbum.setOnClickListener(v -> {
            LiveEventBus.get("user_icon_action", Integer.class)
                    .post(2);
            dismiss();
        });

        binding.cancel.setOnClickListener(v -> dismiss());
    }
}
