package com.cdtde.chongdetang.view.index.search;

import android.os.Bundle;
import android.text.Editable;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.FragmentAdapter;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActivitySearchBinding;
import com.cdtde.chongdetang.requester.SearchRequester;
import com.cdtde.chongdetang.utils.DialogUtil;
import com.cdtde.chongdetang.utils.WindowUtil;

import java.util.Arrays;
import java.util.List;

public class SearchActivity extends BaseActivity<ActivitySearchBinding> {

    @InjectScope(Scopes.APPLICATION)
    private SearchRequester requester;
    @InjectScope(Scopes.ACTIVITY)
    private States states;
    @InjectScope(Scopes.APPLICATION)
    private SearchFragment.Messenger messenger;
    @InjectScope(Scopes.APPLICATION)
    private SearchCollectionFragment.Messenger collectionMessenger;
    @InjectScope(Scopes.APPLICATION)
    private SearchProductFragment.Messenger productMessenger;

    public static class States extends StateHolder {

        public final State<String> content = new State<>("");
        public final State<Integer> page = new State<>(0);
        public final List<Fragment> fragments = Arrays.asList(
                new SearchFragment(),
                new SearchListFragment()
        );
    }

    public class Handler {
        public final OnClickListener search = v ->
                requester.searchCollection(states.content.getValue(),
                        collections -> {
                            collectionMessenger.showEvent.send(collections);
                            requester.searchProduct(states.content.getValue(),
                                    products -> {
                                        productMessenger.showEvent.send(products);
                                        messenger.contentEvent.reply(states.content.getValue());
                                        states.page.setValue(1);
                                    },
                                    ToastUtils::showShort
                            );
                        },
                        ToastUtils::showShort
                );

        public final OnClickListener clearContent = v -> {
            states.content.setValue("");
            if (states.page.getValue() != 0) {
                states.page.setValue(0);
            }
        };

        public void setContent(Editable s) {
            states.content.setValue(s.toString());
            if ("".equals(s.toString()) && states.page.getValue() != 0) {
                states.page.setValue(0);
            }
        }
    }


    @Override
    protected ViewConfig configBinding() {
        requester.registerObserver(DialogUtil.createNetLoading(this), this);
        return new ViewConfig(R.layout.activity_search)
                .add(BR.state, states)
                .add(BR.handler, new Handler())
                .add(BR.adapter, new FragmentAdapter(this));
    }

    @Override
    protected void initUIComponent(@NonNull ActivitySearchBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
        binding.viewPager.setUserInputEnabled(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        messenger.contentEvent.observeSend(this, states.content::setValue);
    }
}