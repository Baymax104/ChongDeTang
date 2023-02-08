package com.cdtde.chongdetang.view.index.appoint;

import android.content.Context;

import androidx.annotation.NonNull;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.DialogAppointBinding;
import com.cdtde.chongdetang.viewModel.index.AppointViewModel;
import com.lxj.xpopup.core.BottomPopupView;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/8 17:10
 * @Version 1
 */
public class AppointDialog extends BottomPopupView {

    public AppointDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_appoint;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        DialogAppointBinding binding = DialogAppointBinding.bind(getPopupImplView());

    }
}
