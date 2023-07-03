package com.cdtde.chongdetang.view.exhibit;

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
import com.cdtde.chongdetang.entity.Collection;
import com.cdtde.chongdetang.repository.UserStore;
import com.cdtde.chongdetang.requester.ExhibitRequester;
import com.cdtde.chongdetang.utils.Starter;
import com.cdtde.chongdetang.view.my.login.LoginActivity;
import com.lxj.xpopup.core.AttachPopupView;

import kotlin.Unit;


public class UserCollectionDialog extends AttachPopupView {

    private States states = DialogScope.getFromActivity(this, States.class);
    private Messenger messenger = DialogScope.getFromApplication(Messenger.class);
    private ExhibitRequester requester = DialogScope.getFromApplication(ExhibitRequester.class);


    public static class States extends StateHolder {
        public final State<Collection> collection = new State<>(new Collection());
    }

    public static class Messenger extends MessageHolder {
        public final Event<Collection, Unit> clickEvent = new Event<>();
    }


    public UserCollectionDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_user_collection;
    }

    public class Handler {
        public OnClickListener collect = v -> {
            if (UserStore.isLogin()) {
                boolean collected = !states.collection.getValue().isUserCollect();
                requester.updateUserCollection(states.collection.getValue(),
                        collection -> collection.setUserCollect(collected),
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

        messenger.clickEvent.observeSend(this, true, states.collection::setValue);

    }

}
