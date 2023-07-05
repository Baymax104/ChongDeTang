package com.cdtde.chongdetang.view.my.order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.recycler.UserOrderAdapter;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.BaseAdapter;
import com.cdtde.chongdetang.base.view.BaseAdapter.ListHandlerFactory;
import com.cdtde.chongdetang.base.view.BindingConfig;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActivityUserOrderBinding;
import com.cdtde.chongdetang.entity.Order;
import com.cdtde.chongdetang.requester.OrderRequester;
import com.cdtde.chongdetang.utils.DialogUtil;
import com.cdtde.chongdetang.utils.Starter;
import com.cdtde.chongdetang.utils.WindowUtil;

import java.util.ArrayList;
import java.util.List;

public class UserOrderActivity extends BaseActivity<ActivityUserOrderBinding> {

    @InjectScope(Scopes.ACTIVITY)
    private States states;
    @InjectScope(Scopes.APPLICATION)
    private OrderRequester requester;
    @InjectScope(Scopes.APPLICATION)
    private OrderDetailActivity.Messenger detailMessenger;
    @InjectScope(Scopes.APPLICATION)
    private OrderDeleteDialog.Messenger deleteMessenger;
    @InjectScope(Scopes.APPLICATION)
    private OrderConfirmDialog.Messenger confirmMessenger;

    public static class States extends StateHolder {
        public final State<List<Order>> orders = new State<>(new ArrayList<>());
    }

    public class ListHandler extends ListHandlerFactory {
        public final OnItemClickListener<Order> itemClick = (data, view) -> {
            Starter.actionStart(activity, OrderDetailActivity.class);
            detailMessenger.showEvent.send(data);
        };

        public final OnItemClickListener<Order> confirm = (data, view) -> {
            DialogUtil.create(activity, OrderConfirmDialog.class).show();
            confirmMessenger.confirmEvent.send(data);
        };

        public final OnItemClickListener<Order> delete = (data, view) -> {
            DialogUtil.create(activity, OrderDeleteDialog.class).show();
            deleteMessenger.deleteEvent.send(data);
        };

        @Override
        public BindingConfig getBindingConfig() {
            return new BindingConfig()
                    .add(BR.itemClick, itemClick)
                    .add(BR.confirmClick, confirm)
                    .add(BR.deleteClick, delete);
        }
    }

    @Override
    protected ViewConfig configBinding() {
        requester.registerObserver(DialogUtil.createNetLoading(this), this);
        UserOrderAdapter adapter = new UserOrderAdapter();
        adapter.setFactory(new ListHandler());
        return new ViewConfig(R.layout.activity_user_order)
                .add(BR.state, states)
                .add(BR.adapter, adapter);
    }

    @Override
    protected void initUIComponent(@NonNull ActivityUserOrderBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requester.getAllOrder(states.orders::setValue, ToastUtils::showShort);

        confirmMessenger.confirmEvent.observeReply(this, value ->
                requester.confirmOrder(value,
                o -> {
                    value.setStatus("SUCCESS");
                    ToastUtils.showShort("收货成功");
                }, ToastUtils::showShort));

        deleteMessenger.deleteEvent.observeReply(this, value ->
                requester.removeOrder(value,
                states.orders::setValue, ToastUtils::showShort));
    }
}