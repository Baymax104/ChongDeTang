package com.cdtde.chongdetang.view.shop;

import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.CheckBox;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.recycler.ShoppingAdapter;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.BaseAdapter.ListHandlerFactory;
import com.cdtde.chongdetang.base.view.BindingConfig;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActivityShoppingBinding;
import com.cdtde.chongdetang.entity.CheckedShopping;
import com.cdtde.chongdetang.entity.Shopping;
import com.cdtde.chongdetang.repository.UserStore;
import com.cdtde.chongdetang.requester.ShoppingRequester;
import com.cdtde.chongdetang.utils.DialogUtil;
import com.cdtde.chongdetang.utils.Starter;
import com.cdtde.chongdetang.utils.WindowUtil;
import com.cdtde.chongdetang.utils.binding.CheckBoxAdapter.OnCheckBoxClick;
import com.cdtde.chongdetang.view.my.login.LoginActivity;
import com.cdtde.chongdetang.view.shop.order.OrderActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingActivity extends BaseActivity<ActivityShoppingBinding> {

    @InjectScope(Scopes.APPLICATION)
    private ShoppingRequester requester;

    @InjectScope(Scopes.ACTIVITY)
    private States states;
    @InjectScope(Scopes.APPLICATION)
    private ProductActivity.Messenger messenger;
    @InjectScope(Scopes.APPLICATION)
    private OrderActivity.Messenger orderMessenger;
    @InjectScope(Scopes.APPLICATION)
    private ShoppingDeleteDialog.Messenger deleteMessenger;

    public static class States extends StateHolder {
        public final State<List<CheckedShopping>> checkedShoppings = new State<>(new ArrayList<>());
        public final State<Integer> selected = new State<>(0);
        public final State<Double> priceSum = new State<>(0.);
        public final State<Boolean> editEnabled = new State<>(false);
    }

    public class Handler {
        public final OnCheckBoxClick onCheckBoxClick = isChecked -> {
            states.checkedShoppings.getValue()
                    .forEach(checkedShopping -> checkedShopping.setChecked(isChecked));
            if (isChecked) {
                double price = states.checkedShoppings.getValue().stream()
                        .filter(CheckedShopping::isChecked)
                        .map(CheckedShopping::getShopping)
                        .mapToDouble(value -> value.getNumber() * value.getProduct().getPrice())
                        .reduce(Double::sum)
                        .orElse(0.);
                states.selected.setValue(states.checkedShoppings.getValue().size());
                states.priceSum.setValue(price);
            } else {
                states.selected.setValue(0);
                states.priceSum.setValue(0.);
            }
        };

        public final OnClickListener edit = v -> {
            states.editEnabled.setValue(!states.editEnabled.getValue());
            states.checkedShoppings.getValue()
                    .forEach(item -> item.setEditEnabled(states.editEnabled.getValue()));
        };

        public final OnClickListener buy = v -> {
            if (UserStore.isLogin()) {
                List<Shopping> shoppings = states.checkedShoppings.getValue().stream()
                        .filter(CheckedShopping::isChecked)
                        .map(CheckedShopping::getShopping)
                        .collect(Collectors.toList());
                orderMessenger.confirmOrderEvent.send(shoppings);
                Starter.actionStart(activity, OrderActivity.class);
            } else {
                Starter.actionStart(activity, LoginActivity.class);
            }
        };
    }

    public class ListHandler extends ListHandlerFactory {
        public final OnItemClickListener<CheckedShopping> itemClick = (data, view) -> {
            messenger.showEvent.send(data.getShopping().getProduct());
            Starter.actionStart(activity, ProductActivity.class);
        };

        public final OnItemClickListener<CheckedShopping> subtract = (data, view) -> {
            if (data.getShopping().getNumber() <= 1) {
                ToastUtils.showShort("商品不能再减少了");
                return;
            }
            int n = data.getShopping().getNumber() - 1;
            requester.updateShoppingNumber(data, n,
                    integer -> {
                        data.getShopping().setNumber(n);
                        if (data.isChecked()) {
                            Double price = states.priceSum.getValue() - data.getShopping().getProduct().getPrice();
                            states.priceSum.setValue(price);
                        }
                    }, ToastUtils::showShort);
        };

        public final OnItemClickListener<CheckedShopping> add = (data, view) -> {
            if (data.getShopping().getNumber() >= 100) {
                ToastUtils.showShort("商品不能再增加了");
                return;
            }
            int n = data.getShopping().getNumber() + 1;
            requester.updateShoppingNumber(data, n,
                    integer -> {
                        data.getShopping().setNumber(n);
                        if (data.isChecked()) {
                            Double price = states.priceSum.getValue() + data.getShopping().getProduct().getPrice();
                            states.priceSum.setValue(price);
                        }
                    }, ToastUtils::showShort);
        };

        public final OnItemClickListener<CheckedShopping> check = (data, view) -> {
            boolean isChecked = ((CheckBox) view).isChecked();
            int numChange = isChecked ? 1 : -1;
            data.setChecked(isChecked);
            states.selected.setValue(states.selected.getValue() + numChange);
            Double price = states.priceSum.getValue() +
                    numChange * data.getShopping().getNumber() * data.getShopping().getProduct().getPrice();
            states.priceSum.setValue(price);
        };

        public final OnItemClickListener<CheckedShopping> delete = (data, view) -> {
            DialogUtil.create(activity, ShoppingDeleteDialog.class).show();
            deleteMessenger.deleteEvent.send(data);
        };

        @Override
        public BindingConfig getBindingConfig() {
            return new BindingConfig()
                    .add(BR.itemClick, itemClick)
                    .add(BR.subtract, subtract)
                    .add(BR.add, add)
                    .add(BR.check, check)
                    .add(BR.delete, delete);
        }
    }

    @Override
    protected ViewConfig configBinding() {
        ShoppingAdapter adapter = new ShoppingAdapter();
        adapter.setFactory(new ListHandler());
        requester.registerObserver(DialogUtil.createNetLoading(this), this);
        return new ViewConfig(R.layout.activity_shopping)
                .add(BR.state, states)
                .add(BR.adapter, adapter)
                .add(BR.handler, new Handler());
    }

    @Override
    protected void initUIComponent(@NonNull ActivityShoppingBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requester.updateShopping(checkedShoppings -> {
            checkedShoppings.forEach(checkedShopping -> checkedShopping.setChecked(true));
            states.checkedShoppings.setValue(checkedShoppings);
            states.selected.setValue(checkedShoppings.size());
            states.priceSum.setValue(refreshPrice(checkedShoppings));
        }, ToastUtils::showShort);

        deleteMessenger.deleteEvent.observeReply(this, value ->
                requester.deleteShopping(value,
                o -> {
                    List<CheckedShopping> list = states.checkedShoppings.getValue();
                    list.remove(value);
                    states.checkedShoppings.setValue(list);
                    if (value.isChecked()) {
                        states.selected.setValue(states.selected.getValue() - 1);
                    }
                    states.priceSum.setValue(refreshPrice(states.checkedShoppings.getValue()));
                }, ToastUtils::showShort));
    }

    private double refreshPrice(List<CheckedShopping> list) {
        return list.stream()
                .filter(CheckedShopping::isChecked)
                .map(CheckedShopping::getShopping)
                .mapToDouble(value -> value.getNumber() * value.getProduct().getPrice())
                .reduce(Double::sum)
                .orElse(0.);
    }

}