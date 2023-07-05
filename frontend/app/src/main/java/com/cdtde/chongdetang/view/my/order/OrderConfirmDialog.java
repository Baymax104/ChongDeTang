package com.cdtde.chongdetang.view.my.order;

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
import com.cdtde.chongdetang.entity.Order;
import com.lxj.xpopup.core.CenterPopupView;

/**
 * @ClassName OrderDeleteDialog
 * @Author John
 * @Date 2023/7/5 22:53
 * @Version 1.0
 */
public class OrderConfirmDialog extends CenterPopupView {

    private States states = DialogScope.getFromActivity(this, States.class);

    private Messenger messenger = DialogScope.getFromApplication(Messenger.class);

    public static class States extends StateHolder {
        public final State<Order> order = new State<>(new Order());
    }

    public static class Messenger extends MessageHolder {
        public final Event<Order, Order> confirmEvent = new Event<>();
    }

    public class Handler {
        public final OnClickListener cancel = v -> dismiss();

        public final OnClickListener confirm = v -> {
            messenger.confirmEvent.reply(states.order.getValue());
            dismiss();
        };
    }

    public OrderConfirmDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_order_confirm;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        DialogBinder.bind(this, new BindingConfig().add(BR.handler, new Handler()));
        messenger.confirmEvent.observeSend(this, true, states.order::setValue);
    }
}
