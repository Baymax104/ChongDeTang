package com.cdtde.chongdetang.view.index;

import android.os.Bundle;
import android.text.Editable;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActivitySearchBinding;
import com.cdtde.chongdetang.utils.WindowUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class SearchActivity extends BaseActivity<ActivitySearchBinding> {


    @InjectScope(Scopes.ACTIVITY)
    private States states;

    public static class States extends StateHolder {

        public final State<String> content = new State<>("");

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
                "书签护身符",
                "德文化书简装签套装",
                "德福-厚德载福条屏",
                "堂主作品（舍念清净）",
                "丝网印帆布包",
                "德福门神"
        );
    }

    public class Handler {
        public final OnClickListener search = v -> {
            LinkedList<String> list = (LinkedList<String>) states.histories.getValue();
            list.addFirst(states.content.getValue());
            if (list.size() > 10) {
                list.removeLast();
            }
            states.histories.setValue(list);
            if (states.isEmpty.getValue()) {
                states.isEmpty.setValue(false);
            }
            ToastUtils.showShort("点击");
        };

        public final OnClickListener clear = v -> {
            states.histories.setValue(new ArrayList<>());
            states.isEmpty.setValue(true);
        };

        public final Consumer<String> contentConsumer = states.content::setValue;

        public void setContent(Editable s) {
            states.content.setValue(s.toString());
        }
    }


    @Override
    protected ViewConfig configBinding() {
        return new ViewConfig(R.layout.activity_search)
                .add(BR.state, states)
                .add(BR.handler, new Handler());
    }

    @Override
    protected void initUIComponent(@NonNull ActivitySearchBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}