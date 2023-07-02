package com.cdtde.chongdetang.view.shop;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BindingConfig;
import com.cdtde.chongdetang.base.view.DialogBinder;
import com.cdtde.chongdetang.base.vm.DialogScope;
import com.cdtde.chongdetang.base.vm.MessageHolder;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.repository.UserStore;
import com.cdtde.chongdetang.requester.ProductRequester;
import com.cdtde.chongdetang.utils.Starter;
import com.cdtde.chongdetang.view.my.login.LoginActivity;
import com.lxj.xpopup.core.AttachPopupView;

import kotlin.Unit;


/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/3/13 13:32
 * @Version 1
 */
public class UserProductDialog extends AttachPopupView {

    private States states = DialogScope.getFromActivity(this, States.class);

    private Messenger messenger = DialogScope.getFromApplication(Messenger.class);

    private ProductRequester requester = DialogScope.getFromApplication(ProductRequester.class);


    public static class States extends StateHolder {
        public final State<Product> product = new State<>(new Product());
    }

    public static class Messenger extends MessageHolder {
        public final Event<Product, Unit> clickEvent = new Event<>();
    }


    public UserProductDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_user_product;
    }

    public class Handler {
        public OnClickListener collect = v -> {
            if (UserStore.isLogin()) {
                boolean collected = !states.product.getValue().isUserCollect();
                requester.updateUserProduct(states.product.getValue(),
                        product -> product.setUserCollect(collected),
                        ToastUtils::showShort);
            } else {
                dismissWith(() -> Starter.actionStart(getContext(), LoginActivity.class));
            }
        };
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        DialogBinder.bind(this, new BindingConfig()
                .add(BR.state, states)
                .add(BR.handler, new Handler()));

        messenger.clickEvent.observeSend(this, true, states.product::setValue);

    }

}
