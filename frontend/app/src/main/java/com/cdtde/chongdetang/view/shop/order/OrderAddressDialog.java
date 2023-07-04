package com.cdtde.chongdetang.view.shop.order;

import android.content.Context;

import androidx.annotation.NonNull;

import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.recycler.OrderAddressAdapter;
import com.cdtde.chongdetang.base.view.BaseAdapter.ListHandlerFactory;
import com.cdtde.chongdetang.base.view.BindingConfig;
import com.cdtde.chongdetang.base.view.DialogBinder;
import com.cdtde.chongdetang.base.vm.DialogScope;
import com.cdtde.chongdetang.base.vm.MessageHolder;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.entity.Address;
import com.lxj.xpopup.core.BottomPopupView;

import java.util.ArrayList;
import java.util.List;


public class OrderAddressDialog extends BottomPopupView {

    private States states = DialogScope.getFromActivity(this, States.class);

    private Messenger messenger = DialogScope.getFromApplication(Messenger.class);

    public static class States extends StateHolder {
        public final State<List<Address>> addresses = new State<>(new ArrayList<>());
    }

    public static class Messenger extends MessageHolder {
        public final Event<List<Address>, Address> selectEvent = new Event<>();
    }

    public class ListHandler extends ListHandlerFactory {

        public final OnItemClickListener<Address> itemClick = (data, view) -> {
            messenger.selectEvent.reply(data);
            dismiss();
        };

        @Override
        public BindingConfig getBindingConfig() {
            return new BindingConfig().add(BR.itemClick, itemClick);
        }
    }

    public OrderAddressDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_order_address;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        OrderAddressAdapter adapter = new OrderAddressAdapter();
        adapter.setFactory(new ListHandler());
        DialogBinder.bind(this, new BindingConfig()
                .add(BR.state, states)
                .add(BR.adapter, adapter));

        messenger.selectEvent.observeSend(this, true, states.addresses::setValue);
    }
}
