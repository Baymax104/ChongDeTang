package com.cdtde.chongdetang.view.my.address;

import android.os.Bundle;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.StringUtils;
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
import com.cdtde.chongdetang.requester.my.AddressRequester;

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
                        activity,
                        (province, city, county) -> {
                            states.address.getValue().setProvince(province.getName());
                            states.address.getValue().setCity(city.getName());
                        }
                ).show();

        public OnClickListener delete = v ->
                DialogUtil.create(activity, DeleteDialog.class).show();
        // TODO：--添加新地址bug
        /*  1 缺少对手机号的验证，比如输入12345678901，会被算作合法手机号
            2 输入框缺少对文字输入个数的限定。如收货人输入30个汉字，会从数据库发出发出"给程序员看的"错误提示，这个提示不该被用户看见
            3 建议详细地址输入框可以随输入行数增多扩展。向"反馈的那个输入框一样"
        */

        public OnClickListener save = v -> {
            Address address = states.address.getValue();
            if (StringUtils.isEmpty(address.getConsignee()) ||
                    StringUtils.isEmpty(address.getPhone()) ||
                    StringUtils.isEmpty(address.getProvince()) ||
                    StringUtils.isEmpty(address.getCity()) ||
                    StringUtils.isEmpty(address.getDetail())) {
                ToastUtils.showShort("地址未填写完整");
            } else {
                requester.updateAddress(
                        states.address.getValue(),
                        a -> {
                            messenger.updateAllEvent.send(Unit.INSTANCE);
                            finish();
                        },
                        ToastUtils::showShort
                );
            }
        };
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

    }
}