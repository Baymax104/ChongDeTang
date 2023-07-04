package com.cdtde.chongdetang.view.shop.order;

import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.recycler.OrderProductAdapter;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.BaseAdapter.ListHandlerFactory.OnItemClickListener;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.MessageHolder;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActivityOrderBinding;
import com.cdtde.chongdetang.entity.Address;
import com.cdtde.chongdetang.entity.Shopping;
import com.cdtde.chongdetang.requester.OrderRequester;
import com.cdtde.chongdetang.utils.DialogUtil;
import com.cdtde.chongdetang.utils.Starter;
import com.cdtde.chongdetang.utils.WindowUtil;
import com.cdtde.chongdetang.view.my.address.AddressDetailActivity;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;

public class OrderActivity extends BaseActivity<ActivityOrderBinding> {

    @InjectScope(Scopes.APPLICATION)
    private OrderRequester requester;
    @InjectScope(Scopes.ACTIVITY)
    private States states;
    @InjectScope(Scopes.APPLICATION)
    private Messenger messenger;
    @InjectScope(Scopes.APPLICATION)
    private AddressDetailActivity.Messenger detailMessenger;
    @InjectScope(Scopes.APPLICATION)
    private PayActivity.Messenger payMessenger;
    @InjectScope(Scopes.APPLICATION)
    private OrderAddressDialog.Messenger addressMessenger;

    public static class States extends StateHolder {
        public final State<List<Shopping>> shoppings = new State<>(new ArrayList<>());
        public final State<Address> address = new State<>(new Address());
        public final State<Double> price = new State<>(0.);
        public final State<Boolean> isAddressEmpty = new State<>(true);
        public List<Address> addresses = new ArrayList<>();
    }

    public static class Messenger extends MessageHolder {
        public final Event<List<Shopping>, Unit> confirmOrderEvent = new Event<>();
    }

    public class Handler {

        public final OnClickListener selectAddress = v -> {
            if (states.isAddressEmpty.getValue()) {
                detailMessenger.showEvent.send(new Address());
                Starter.actionStart(activity, AddressDetailActivity.class);
            } else {
                DialogUtil.create(activity, OrderAddressDialog.class).show();
                addressMessenger.selectEvent.send(states.addresses);
            }
        };

        public final OnItemClickListener<Address> addressClick = (data, view) -> {
            DialogUtil.create(activity, OrderAddressDialog.class).show();
            addressMessenger.selectEvent.send(states.addresses);
        };

        public final OnClickListener pay = v ->
                Starter.actionStart(activity, PayActivity.class);
    }

    @Override
    protected ViewConfig configBinding() {
        return new ViewConfig(R.layout.activity_order)
                .add(BR.state, states)
                .add(BR.handler, new Handler())
                .add(BR.adapter, new OrderProductAdapter());
    }

    @Override
    protected void initUIComponent(@NonNull ActivityOrderBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
        binding.stateLayout.onEmpty((view, o) -> {
            String tip = (String) o;
            TextView tv = view.findViewById(R.id.empty_tip);
            tv.setText(tip);
            return null;
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messenger.confirmOrderEvent.observeSend(this, true, value -> {
            states.shoppings.setValue(value);
            double price = value.stream()
                    .mapToDouble(shopping -> shopping.getNumber() * shopping.getProduct().getPrice())
                    .reduce(Double::sum)
                    .orElse(0.);
            states.price.setValue(price);
        });

        requester.getAllAddress(addresses -> {
            if (addresses != null && !addresses.isEmpty()) {
                states.addresses = addresses;
                states.address.setValue(addresses.get(0));
                states.isAddressEmpty.setValue(false);
            } else {
                states.isAddressEmpty.setValue(true);
            }
        }, ToastUtils::showShort);

        detailMessenger.updateAllEvent.observeSend(this, value ->
                requester.getAllAddress(addresses -> {
                    if (addresses != null && !addresses.isEmpty()) {
                        states.addresses = addresses;
                        states.address.setValue(addresses.get(0));
                        states.isAddressEmpty.setValue(false);
                    } else {
                        states.isAddressEmpty.setValue(true);
                        ToastUtils.showShort("出了点小问题~");
                    }
                }, ToastUtils::showShort));

        payMessenger.payEvent.observeSend(this, value -> {
            // TODO 发送订单
            ToastUtils.showShort("发送订单");
        });

        addressMessenger.selectEvent.observeReply(this, states.address::setValue);
    }
}