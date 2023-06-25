package com.cdtde.chongdetang.view.shop;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BindingConfig;
import com.cdtde.chongdetang.base.view.DialogBinder;
import com.cdtde.chongdetang.base.vm.DialogScope;
import com.cdtde.chongdetang.base.vm.MessageHolder;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.entity.UserCollect;
import com.lxj.xpopup.core.AttachPopupView;

import kotlin.Unit;


/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/3/13 13:32
 * @Version 1
 */
public class ItemCollectDialog extends AttachPopupView {

    private States states = DialogScope.getFromActivity(this, States.class);

    private Messenger messenger = DialogScope.getFromApplication(Messenger.class);


    public static class States extends StateHolder {
        public final State<UserCollect> userCollect = new State<>(new UserCollect());
        public final State<Boolean> isCollected = new State<>(false);
    }

    public static class Messenger extends MessageHolder {
        public String key;
        public final EventWithKey<UserCollect, Unit> clickEvent = new EventWithKey<>();
        public final EventWithKey<UserCollect, String> collectEvent = new EventWithKey<>();
        public final EventWithKey<UserCollect, String> cancelEvent = new EventWithKey<>();
    }


    public ItemCollectDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_collect_menu;
    }

    public class Handler {
        public OnClickListener collect = v -> {
            if (states.isCollected.getValue()) { // 已收藏，取消
                messenger.cancelEvent.send(states.userCollect.getValue(), messenger.key);
            } else { // 未收藏，收藏
                messenger.collectEvent.send(states.userCollect.getValue(), messenger.key);
            }
        };
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        DialogBinder.bind(this, new BindingConfig()
                .add(BR.state, states)
                .add(BR.handler, new Handler()));

        messenger.clickEvent.observeSend(this, true, "./", (value, key) -> {
            states.userCollect.setValue(value);
            states.isCollected.setValue(value.isCollected());
            messenger.key = key;
        });

        messenger.collectEvent.observeReply(this, true, messenger.key,
                (value, key) -> {
                    if ("collection".equals(value)) {
                        states.userCollect.getValue().getCollection().setUserCollect(true);
                    } else {
                        states.userCollect.getValue().getProduct().setUserCollect(true);
                    }
                    states.isCollected.setValue(true);
                });

        messenger.cancelEvent.observeReply(this, true, messenger.key,
                (value, key) -> {
                    if ("collection".equals(value)) {
                        states.userCollect.getValue().getCollection().setUserCollect(false);
                    } else {
                        states.userCollect.getValue().getProduct().setUserCollect(false);
                    }
                    states.isCollected.setValue(false);
                });

    }
}
