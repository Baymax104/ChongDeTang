package com.cdtde.chongdetang.view.my.address;

import android.os.Bundle;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.MessageHolder;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActivityAddressDetailBinding;
import com.cdtde.chongdetang.entity.Address;
import com.cdtde.chongdetang.utils.DialogUtil;
import com.cdtde.chongdetang.utils.WindowUtil;
import com.cdtde.chongdetang.viewModel.my.AddressRequester;

import kotlin.Unit;

public class AddressDetailActivity extends BaseActivity<ActivityAddressDetailBinding> {


    @InjectScope(Scopes.APPLICATION)
    private AddressRequester requester;

    @InjectScope(Scopes.ACTIVITY)
    private States states;

    @InjectScope(Scopes.APPLICATION)
    private AddressActivity.Messenger parentMessenger;

    @InjectScope(Scopes.APPLICATION)
    private Messenger messenger;

    public static class States extends StateHolder {
        public final State<Address> address = new State<>(new Address());
    }

    public static class Messenger extends MessageHolder {
        public final Event<Unit, Unit> deleteEvent = new Event<>();
        public final Event<Unit, Unit> updateAllEvent = new Event<>();
    }


    public class Handler {
        public OnClickListener selectAddress = v ->
                DialogUtil.createAddressPicker(
                        AddressDetailActivity.this,
                        (province, city, county) -> {
                            states.address.getValue().setProvince(province.getName());
                            states.address.getValue().setCity(city.getName());
                        }
                );

        public OnClickListener delete = v ->
                DialogUtil.create(AddressDetailActivity.this, DeleteDialog.class).show();

        public OnClickListener save = v -> requester.updateAddress(
                states.address.getValue(),
                address -> {
                    messenger.updateAllEvent.send(Unit.INSTANCE);
                    finish();
                },
                ToastUtils::showShort
        );
    }

    @Override
    protected ViewConfig configBinding() {
        requester.registerObserver(DialogUtil.createNetLoading(this), this);
        return new ViewConfig(R.layout.activity_address_detail)
                .add(BR.state, states)
                .add(BR.handler, new Handler());
    }

    @Override
    protected void initUIComponent(@NonNull ActivityAddressDetailBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parentMessenger.detailEvent.observeSend(this, true, states.address::setValue);

        messenger.deleteEvent.observeSend(this, value ->
                requester.removeAddress(
                states.address.getValue(),
                address -> {
                    messenger.updateAllEvent.send(Unit.INSTANCE);
                    finish();
                },
                ToastUtils::showShort
        ));


//        LiveEventBus.get("UserRepository-requestUpdateAddress", WebException.class)
//                .observe(this, e -> {
//                    if (e.isSuccess()) {
//                        LiveEventBus.get("AddressDetailActivity-requestUpdateAddress", Boolean.class).post(true);
//                        finish();
//                    } else {
//                        ToastUtils.showShort(e.getMessage());
//                    }
//                });


//        LiveEventBus.get("UserRepository-requestRemoveAddress", WebException.class)
//                .observe(this, e -> {
//                    if (e.isSuccess()) {
//                        LiveEventBus.get("AddressDetailActivity-requestRemoveAddress", Boolean.class).post(true);
//                        finish();
//                    } else {
//                        ToastUtils.showShort(e.getMessage());
//                    }
//                });

    }
}