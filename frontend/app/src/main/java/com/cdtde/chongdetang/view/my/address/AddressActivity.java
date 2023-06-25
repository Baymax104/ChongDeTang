package com.cdtde.chongdetang.view.my.address;

import android.os.Bundle;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.recycler.AddressAdapter;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.BaseAdapter.ListHandlerFactory;
import com.cdtde.chongdetang.base.view.BindingConfig;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.MessageHolder;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActivityAddressBinding;
import com.cdtde.chongdetang.entity.Address;
import com.cdtde.chongdetang.utils.DialogUtil;
import com.cdtde.chongdetang.utils.Starter;
import com.cdtde.chongdetang.utils.WindowUtil;
import com.cdtde.chongdetang.viewModel.my.AddressRequester;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;

public class AddressActivity extends BaseActivity<ActivityAddressBinding> {

    @InjectScope(Scopes.APPLICATION)
    private AddressRequester requester;

    @InjectScope(Scopes.ACTIVITY)
    private States states;

    @InjectScope(Scopes.APPLICATION)
    private Messenger messenger;

    @InjectScope(Scopes.APPLICATION)
    private AddressDetailActivity.Messenger detailMessenger;

    public static class States extends StateHolder {
        public final State<List<Address>> addresses = new State<>(new ArrayList<>());
    }

    public static class Messenger extends MessageHolder {
        public final Event<Address, Unit> detailEvent = new Event<>();
    }

    public class Handler {
        public OnClickListener add = v -> {
            messenger.detailEvent.send(new Address());
            Starter.actionStart(AddressActivity.this, AddressDetailActivity.class);
        };
    }

    public class ListHandler extends ListHandlerFactory {

        public OnItemClickListener<Address> modify = (data, view) -> {
            messenger.detailEvent.send(data);
            Starter.actionStart(AddressActivity.this, AddressDetailActivity.class);
        };

        @Override
        public BindingConfig getBindingConfig() {
            return new BindingConfig().add(BR.modify, modify);
        }
    }

    @Override
    protected ViewConfig configBinding() {
        AddressAdapter adapter = new AddressAdapter();
        adapter.setFactory(new ListHandler());
        requester.registerObserver(DialogUtil.createNetLoading(this), this);

        return new ViewConfig(R.layout.activity_address)
                .add(BR.state, states)
                .add(BR.handler, new Handler())
                .add(BR.adapter, adapter);
    }

    @Override
    protected void initUIComponent(@NonNull ActivityAddressBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailMessenger.updateAllEvent.observeSend(this, value ->
                requester.updateAllAddress(states.addresses::setValue, ToastUtils::showShort));


//        LiveEventBus.get("UserRepository-requestAllAddress", WebException.class)
//                .observe(this, e -> {
//                    if (e.isSuccess()) {
//                        states.addresses.setValue(requester.refreshAllAddress());
//                    } else {
//                        ToastUtils.showShort(e.getMessage());
//                    }
//                });

//        LiveEventBus.get("AddressDetailActivity-requestUpdateAddress", Boolean.class)
//                .observe(this, aBoolean -> {
//                            if (aBoolean) {
//                                requester.updateAllAddress();
//                            }
//                        });
//
//        LiveEventBus.get("AddressDetailActivity-requestRemoveAddress", Boolean.class)
//                        .observe(this, aBoolean -> {
//                            if (aBoolean) {
//                                requester.updateAllAddress();
//                            }
//                        });
        requester.updateAllAddress(states.addresses::setValue, ToastUtils::showShort);
    }

}
