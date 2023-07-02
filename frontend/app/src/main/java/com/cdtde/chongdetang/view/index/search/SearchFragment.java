package com.cdtde.chongdetang.view.index.search;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseFragment;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.MessageHolder;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import kotlin.Unit;

/**
 * @ClassName SearchFragment
 * @Author John
 * @Date 2023/7/2 16:56
 * @Version 1.0
 */
public class SearchFragment extends BaseFragment<FragmentSearchBinding> {

    @InjectScope(Scopes.FRAGMENT)
    private States states;
    @InjectScope(Scopes.APPLICATION)
    private Messenger messenger;


    public static class States extends StateHolder {

        public final State<Boolean> isEmpty = new State<>(true);
        public final State<List<String>> histories = new State<>(new LinkedList<>());

        public final List<String> collections = Arrays.asList(
                "王琦书法作品 明德惟馨",
                "贾若愚书法作品 俭以养德",
                "焦亮篆刻作品 德尊望重",
                "功德匾 祖德流芳",
                "牌坊匾 旌德",
                "功德匾 德重桑梓"
        );

        public final List<String> products = Arrays.asList(
                "当思历",
                "德文化书简装签套装",
                "德福-厚德载福条屏",
                "堂主作品（舍念清净）",
                "丝网印帆布包",
                "德福门神"
        );

    }

    public static class Messenger extends MessageHolder {
        public final Event<String, String> contentEvent = new Event<>();
    }

    public class Handler {

        public final View.OnClickListener clear = v -> {
            states.histories.setValue(new ArrayList<>());
            states.isEmpty.setValue(true);
        };

        public final Consumer<String> contentConsumer = messenger.contentEvent::send;

    }

    @Override
    protected ViewConfig configBinding() {
        return new ViewConfig(R.layout.fragment_search)
                .add(BR.state, states)
                .add(BR.handler, new Handler());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        messenger.contentEvent.observeReply(getViewLifecycleOwner(), value -> {
            LinkedList<String> list = (LinkedList<String>) states.histories.getValue();
            list.addFirst(value);
            if (list.size() > 10) {
                list.removeLast();
            }
            states.histories.setValue(list);
            if (states.isEmpty.getValue()) {
                states.isEmpty.setValue(false);
            }
        });
    }
}
