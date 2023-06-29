package com.cdtde.chongdetang.view.shop;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.recycler.ShopProductAdapter;
import com.cdtde.chongdetang.base.view.BaseAdapter.ListHandlerFactory;
import com.cdtde.chongdetang.base.view.BaseAdapter.ListHandlerFactory.OnItemClickListener;
import com.cdtde.chongdetang.base.view.BaseFragment;
import com.cdtde.chongdetang.base.view.BindingConfig;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.FragmentShopBinding;
import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.entity.UserCollect;
import com.cdtde.chongdetang.repository.UserStore;
import com.cdtde.chongdetang.utils.DialogUtil;
import com.cdtde.chongdetang.utils.Starter;
import com.cdtde.chongdetang.view.index.SearchActivity;
import com.cdtde.chongdetang.view.my.login.LoginActivity;
import com.cdtde.chongdetang.requester.shop.ShopRequester;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/21 22:03
 * @Version 1
 */
public class ShopFragment extends BaseFragment<FragmentShopBinding> {

    @InjectScope(Scopes.APPLICATION)
    private ShopRequester requester;
    private ItemCollectDialog collectDialog;

    @InjectScope(Scopes.FRAGMENT)
    private States states;
    @InjectScope(Scopes.APPLICATION)
    private ItemCollectDialog.Messenger collectMessenger;
    @InjectScope(Scopes.APPLICATION)
    private ProductActivity.Messenger productMessenger;


    public static class States extends StateHolder {
        public final State<List<Product>> products = new State<>(new ArrayList<>());
        public final State<List<Product>> hotProducts = new State<>(new ArrayList<>());
        public final State<Product> showHot1 = new State<>(new Product());
        public final State<Product> showHot2 = new State<>(new Product());
        public final State<Product> showHot3 = new State<>(new Product());

        public final List<Integer> banner = Arrays.asList(
                R.drawable.shop_banner1,
                R.drawable.shop_banner2,
                R.drawable.shop_banner3
        );

    }

    public class Handler {
        public final OnItemClickListener<Product> hotClick = (data, view) -> {
            productMessenger.showEvent.send(data);
            Starter.actionStart(activity, ProductActivity.class);
        };

        public final OnClickListener shoppingEntry = v -> {
            if (UserStore.isLogin()) {
                Starter.actionStart(activity, ShoppingActivity.class);
            } else {
                Starter.actionStart(activity, LoginActivity.class);
            }
        };

        public final OnMenuItemClickListener onMenuItemClick = item -> {
            int id = item.getItemId();
            if (id == R.id.index_search) {
                SearchActivity.actionStart(getContext());
            }
            return true;
        };
    }

    public class ListHandler extends ListHandlerFactory {
        public final OnItemClickListener<Product> onItemClick = (data, view) -> {
            productMessenger.showEvent.send(data);
            Starter.actionStart(activity, ProductActivity.class);
        };

        public final OnItemClickListener<Product> moreClick = (data, view) -> {
            collectMessenger.clickEvent.send(new UserCollect(data), "ShopFragment");
            collectDialog = DialogUtil.createAttachDialog(activity, ItemCollectDialog.class, view);
            collectDialog.show();
        };

        @Override
        public BindingConfig getBindingConfig() {
            return new BindingConfig()
                    .add(BR.itemClick, onItemClick)
                    .add(BR.moreClick, moreClick);
        }
    }

    @Override
    protected ViewConfig configBinding() {
        ShopProductAdapter adapter = new ShopProductAdapter();
        adapter.setFactory(new ListHandler());
        return new ViewConfig(R.layout.fragment_shop)
                .add(BR.state, states)
                .add(BR.productAdapter, adapter)
                .add(BR.handler, new Handler());
    }

    @Override
    protected void initUIComponent(@NonNull FragmentShopBinding binding) {
        binding.toolbar.inflateMenu(R.menu.index_toolbar);
        binding.banner.setIndicator(new CircleIndicator(getContext()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        requester.updateAllProduct(states.products::setValue, ToastUtils::showShort);
        requester.updateHotProduct(products -> {
            states.hotProducts.setValue(products);
            states.showHot1.setValue(products.get(0));
            states.showHot2.setValue(products.get(1));
            states.showHot3.setValue(products.get(2));
        }, ToastUtils::showShort);

        UserStore.getUserLoginEvent().observeSend(getViewLifecycleOwner(), true, value ->
                requester.updateAllProduct(states.products::setValue, ToastUtils::showShort));

        collectMessenger.collectEvent.observeSend(getViewLifecycleOwner(), "ShopFragment", (value, key) -> {
            if (UserStore.isLogin()) {
                requester.addUserProduct(value.getProduct(),
                        product -> collectMessenger.collectEvent.reply("product", "ShopFragment"),
                        ToastUtils::showShort);
            } else {
                collectDialog.dismissWith(() ->
                        Starter.actionStart(activity, LoginActivity.class)
                );
            }
        });

        collectMessenger.cancelEvent.observeSend(getViewLifecycleOwner(), "ShopFragment", (value, key) ->
                requester.removeUserProduct(value.getProduct(),
                        product -> collectMessenger.cancelEvent.reply("product", "ShopFragment"),
                        ToastUtils::showShort));
    }

}
