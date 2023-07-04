package com.cdtde.chongdetang.view.my.address;

import android.content.Context;

import androidx.annotation.NonNull;

import com.cdtde.chongdetang.BR;
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
 * @Date 2023/2/6 16:14
 * @Version 1
 */
public class AddressDeleteDialog extends CenterPopupView {

    private Messenger messenger = DialogScope.getFromApplication(Messenger.class);

    public static class Messenger extends MessageHolder {
        public final Event<Unit, Unit> deleteEvent = new Event<>();
    }

    public AddressDeleteDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_address_delete;
    }

    public class Handler {
        public OnClickListener confirm = v -> {
            messenger.deleteEvent.send(Unit.INSTANCE);
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
