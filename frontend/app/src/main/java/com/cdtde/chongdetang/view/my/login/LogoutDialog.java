package com.cdtde.chongdetang.view.my.login;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BindingConfig;
import com.cdtde.chongdetang.base.view.DialogBinder;
import com.cdtde.chongdetang.base.vm.DialogScope;
import com.cdtde.chongdetang.base.vm.MessageHolder;
import com.lxj.xpopup.core.CenterPopupView;

import kotlin.Unit;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/15 20:48
 * @Version 1
 */
public class LogoutDialog extends CenterPopupView {

    private Messenger messenger = DialogScope.getFromApplication(Messenger.class);

    public static class Messenger extends MessageHolder {
        public final Event<Unit, Unit> logout = new Event<>();
    }

    public LogoutDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_logout;
    }

    public class Handler {
        public OnClickListener confirm = v -> {
            messenger.logout.send(Unit.INSTANCE);
            dismiss();
        };

        public OnClickListener cancel = v -> dismiss();
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        DialogBinder.bind(this, new BindingConfig().add(BR.handler, new Handler()));
    }
}
