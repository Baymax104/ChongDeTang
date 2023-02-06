package com.cdtde.chongdetang.view.my.address;

import android.content.Context;

import androidx.annotation.NonNull;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.DialogAddressDeleteBinding;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.core.CenterPopupView;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/6 16:14
 * @Version 1
 */
public class DeleteDialog extends CenterPopupView {

    public DeleteDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_address_delete;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        DialogAddressDeleteBinding binding = DialogAddressDeleteBinding.bind(getPopupImplView());
        binding.setLifecycleOwner(this);

        binding.cancel.setOnClickListener(v -> dismiss());

        binding.confirm.setOnClickListener(v -> {
            LiveEventBus.get("DeleteDialog-delete", Boolean.class).post(true);
            dismiss();
        });
    }
}
