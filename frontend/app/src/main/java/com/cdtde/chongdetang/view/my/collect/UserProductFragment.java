package com.cdtde.chongdetang.view.my.collect;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.recycler.UserProductAdapter;
import com.cdtde.chongdetang.base.view.BaseAdapter.ListHandlerFactory;
import com.cdtde.chongdetang.base.view.BaseFragment;
import com.cdtde.chongdetang.base.view.BindingConfig;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.FragmentUserProductBinding;
import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.requester.my.UserCollectRequester;
import com.cdtde.chongdetang.utils.DialogUtil;
import com.cdtde.chongdetang.utils.Starter;
import com.cdtde.chongdetang.view.shop.ProductActivity;
import com.cdtde.chongdetang.view.shop.UserProductDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/24 20:40
 * @Version 1
 */
public class UserProductFragment extends BaseFragment<FragmentUserProductBinding> {

    @InjectScope(Scopes.ACTIVITY)
    private UserCollectRequester requester;
    @InjectScope(Scopes.FRAGMENT)
    private States states;
    @InjectScope(Scopes.APPLICATION)
    private ProductActivity.Messenger productMessenger;

    public static class States extends StateHolder {
        public State<List<Product>> products = new State<>(new ArrayList<>());
    }

    public class ListHandler extends ListHandlerFactory {
        public OnItemClickListener<Product> itemClick = (data, view) -> {
            productMessenger.showEvent.send(data);
            Starter.actionStart(activity, ProductActivity.class);
        };

        @Override
        public BindingConfig getBindingConfig() {
            return new BindingConfig()
                    .add(BR.allClick, itemClick);
        }
    }

    @Override
    protected ViewConfig configBinding() {
        UserProductAdapter adapter = new UserProductAdapter();
        adapter.setFactory(new ListHandler());
        return new ViewConfig(R.layout.fragment_user_product)
                .add(BR.state, states)
                .add(BR.adapter, adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requester.updateUserProduct(states.products::setValue, ToastUtils::showShort);
    }
}
