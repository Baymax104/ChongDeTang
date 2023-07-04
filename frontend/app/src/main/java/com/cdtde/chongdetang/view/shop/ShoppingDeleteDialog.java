package com.cdtde.chongdetang.view.shop;

import android.content.Context;

import androidx.annotation.NonNull;

import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BindingConfig;
import com.cdtde.chongdetang.base.view.DialogBinder;
import com.cdtde.chongdetang.base.vm.DialogScope;
import com.cdtde.chongdetang.base.vm.MessageHolder;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.entity.CheckedShopping;
import com.cdtde.chongdetang.entity.Shopping;
import com.lxj.xpopup.core.CenterPopupView;

import kotlin.Unit;

/**
 * @ClassName ShoppingDeleteDialog
 * @Author John
 * @Date 2023/7/4 17:30
 * @Version 1.0
 */
public class ShoppingDeleteDialog extends CenterPopupView {

    private Messenger messenger = DialogScope.getFromApplication(Messenger.class);
    private States states = DialogScope.getFromActivity(this, States.class);


    public static class States extends StateHolder {
        public final State<CheckedShopping> shopping = new State<>(new CheckedShopping());
    }

    public static class Messenger extends MessageHolder {
        public final Event<CheckedShopping, CheckedShopping> deleteEvent = new Event<>();
    }

    public class Handler {
        public final OnClickListener cancel = v -> dismiss();

        public final OnClickListener confirm = v -> {
            messenger.deleteEvent.reply(states.shopping.getValue());
            dismiss();
        };
    }

    public ShoppingDeleteDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_shopping_delete;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        DialogBinder.bind(this, new BindingConfig().add(BR.handler, new Handler()));
        messenger.deleteEvent.observeSend(this, true, states.shopping::setValue);
    }
}
