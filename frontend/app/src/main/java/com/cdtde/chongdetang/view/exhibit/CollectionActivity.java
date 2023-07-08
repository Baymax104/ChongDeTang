package com.cdtde.chongdetang.view.exhibit;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.MessageHolder;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActivityCollectionBinding;
import com.cdtde.chongdetang.entity.Collection;
import com.cdtde.chongdetang.utils.WebViewUtil;
import com.cdtde.chongdetang.utils.WindowUtil;

import kotlin.Unit;

/**
 * 藏品详情页
 */
public class CollectionActivity extends BaseActivity<ActivityCollectionBinding> {


    @InjectScope(Scopes.ACTIVITY)
    private States states;

    @InjectScope(Scopes.APPLICATION)
    private Messenger messenger;

    public static class States extends StateHolder {
        /**
         * 当前展示藏品状态
         */
        public final State<Collection> collection = new State<>(new Collection());
    }

    public static class Messenger extends MessageHolder {
        /**
         * 展示事件
         */
        public final Event<Collection, Unit> showEvent = new Event<>();
    }

    @Override
    protected ViewConfig configBinding() {
        return new ViewConfig(R.layout.activity_collection)
                .add(BR.state, states);
    }

    @Override
    protected void initUIComponent(@NonNull ActivityCollectionBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
        WebViewUtil.configure(binding.webPage, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messenger.showEvent.observeSend(this, true, states.collection::setValue);
    }
}