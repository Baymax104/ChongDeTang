package com.cdtde.chongdetang.view.my.order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.recycler.OrderProductAdapter;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.MessageHolder;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActivityOrderDetailBinding;
import com.cdtde.chongdetang.entity.Order;
import com.cdtde.chongdetang.utils.WindowUtil;

import kotlin.Unit;

public class OrderDetailActivity extends BaseActivity<ActivityOrderDetailBinding> {

    @InjectScope(Scopes.ACTIVITY)
    private States states;
    @InjectScope(Scopes.APPLICATION)
    private Messenger messenger;


    public static class States extends StateHolder {
        public final State<Order> order = new State<>(new Order());
    }

    public static class Messenger extends MessageHolder {
        public final Event<Order, Unit> showEvent = new Event<>();
    }

    @Override
    protected ViewConfig configBinding() {
        OrderProductAdapter adapter = new OrderProductAdapter();
        return new ViewConfig(R.layout.activity_order_detail)
                .add(BR.state, states)
                .add(BR.adapter, adapter);
    }

    @Override
    protected void initUIComponent(@NonNull ActivityOrderDetailBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messenger.showEvent.observeSend(this, true, states.order::setValue);
    }
}