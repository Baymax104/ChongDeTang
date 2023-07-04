package com.cdtde.chongdetang.view.index.origin;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseFragment;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.FragmentOriginBinding;
import com.cdtde.chongdetang.utils.WebViewUtil;


public class OriginFragment extends BaseFragment<FragmentOriginBinding> {

    @InjectScope(Scopes.FRAGMENT)
    private States states;

    public static OriginFragment newInstance(int page) {
        OriginFragment fragment = new OriginFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("page", page);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static class States extends StateHolder {
        public final State<String> url = new State<>("");
        public int page = 0;
    }

    @Override
    protected ViewConfig configBinding() {
        return new ViewConfig(R.layout.fragment_origin)
                .add(BR.state, states);
    }

    @Override
    protected void initUIComponent(@NonNull FragmentOriginBinding binding) {
        WebViewUtil.configure(binding.webPage, true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            states.page = getArguments().getInt("page");
            switch (states.page) {
                case 0:
                    states.url.setValue("http://www.cdtde.com/gywm/zjcdt");
                    break;
                case 1:
                    states.url.setValue("http://www.cdtde.com/gywm/cdtyq");
                    break;
                case 2:
                    states.url.setValue("http://www.cdtde.com/gywm/cdtz");
                    break;
            }
        }
    }

}