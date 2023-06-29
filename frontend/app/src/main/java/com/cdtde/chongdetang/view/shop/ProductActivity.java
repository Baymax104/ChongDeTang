package com.cdtde.chongdetang.view.shop;

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
import com.cdtde.chongdetang.databinding.ActivityProductBinding;
import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.repository.UserStore;
import com.cdtde.chongdetang.utils.DialogUtil;
import com.cdtde.chongdetang.utils.Starter;
import com.cdtde.chongdetang.utils.WindowUtil;
import com.cdtde.chongdetang.view.my.login.LoginActivity;
import com.cdtde.chongdetang.requester.shop.ProductRequester;

import kotlin.Unit;

public class ProductActivity extends BaseActivity<ActivityProductBinding> {

    @InjectScope(Scopes.APPLICATION)
    private ProductRequester requester;
    @InjectScope(Scopes.ACTIVITY)
    private States states;
    @InjectScope(Scopes.APPLICATION)
    private Messenger messenger;

    public static class States extends StateHolder {
        public final State<Product> product = new State<>(new Product());
    }

    public static class Messenger extends MessageHolder {
        public final Event<Product, Unit> showEvent = new Event<>();
    }

    public class Handler {
        public final OnClickListener shoppingEntry = v -> {
            if (UserStore.isLogin()) {
                Starter.actionStart(activity, ShoppingActivity.class);
            } else {
                Starter.actionStart(activity, LoginActivity.class);
            }
        };

        public final OnClickListener addShopping = v -> {
            if (UserStore.isLogin()) {
                requester.addShopping(states.product.getValue(),
                        o -> ToastUtils.showShort("添加成功"),
                        ToastUtils::showShort);
            } else {
                Starter.actionStart(activity, LoginActivity.class);
            }
        };

        public final OnClickListener collect = v -> {
            boolean collected = !states.product.getValue().isUserCollect();
            if (UserStore.isLogin()) {
                requester.updateUserProduct(states.product.getValue(),
                        product -> product.setUserCollect(collected),
                        ToastUtils::showShort);
            } else {
                Starter.actionStart(activity, LoginActivity.class);
            }
        };
    }

    @Override
    protected ViewConfig configBinding() {
        requester.registerObserver(DialogUtil.createNetLoading(this), this);
        return new ViewConfig(R.layout.activity_product)
                .add(BR.state, states)
                .add(BR.handler, new Handler());
    }

    @Override
    protected void initUIComponent(@NonNull ActivityProductBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messenger.showEvent.observeSend(this, true, states.product::setValue);
    }
}