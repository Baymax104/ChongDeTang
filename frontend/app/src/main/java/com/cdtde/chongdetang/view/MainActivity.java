package com.cdtde.chongdetang.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.FragmentAdapter;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.MessageHolder;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActivityMainBinding;
import com.cdtde.chongdetang.view.exhibit.ExhibitFragment;
import com.cdtde.chongdetang.view.index.IndexFragment;
import com.cdtde.chongdetang.view.my.MyFragment;
import com.cdtde.chongdetang.view.shop.ShopFragment;

import java.util.Arrays;
import java.util.List;

import kotlin.Unit;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @InjectScope(Scopes.ACTIVITY)
    private States states;

    @InjectScope(Scopes.APPLICATION)
    private Messenger messenger;

    public static class Messenger extends MessageHolder {
        public final EventWithKey<Unit, Unit> pageEvent = new EventWithKey<>();
        public final Event<Integer, Unit> requestPage = new Event<>();
    }

    public static class States extends StateHolder {
        public final State<Integer> page = new State<>(0);
        public final List<Fragment> fragments = Arrays.asList(
                new IndexFragment(),
                new ExhibitFragment(),
                new ShopFragment(),
                new MyFragment()
        );
    }

    @Override
    protected ViewConfig configBinding() {
        return new ViewConfig(R.layout.activity_main)
                .add(BR.state, states)
                .add(BR.fragmentAdapter, new FragmentAdapter(this))
                .add(BR.handler, new Handler());
    }

    public class Handler {

        public boolean onItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.nav_index) {
                states.page.setValue(0);
                messenger.pageEvent.send(Unit.INSTANCE, "IndexFragment");
            } else if (id == R.id.nav_exhibit) {
                states.page.setValue(1);
                messenger.pageEvent.send(Unit.INSTANCE, "ExhibitFragment");
            } else if (id == R.id.nav_shop) {
                states.page.setValue(2);
                messenger.pageEvent.send(Unit.INSTANCE, "ShopFragment");
            } else if (id == R.id.nav_my) {
                states.page.setValue(3);
                messenger.pageEvent.send(Unit.INSTANCE, "MyFragment");
            }
            return true;
        }
    }

    @Override
    protected void initUIComponent(@NonNull ActivityMainBinding binding) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        binding.viewPager.setUserInputEnabled(false);
        binding.viewPager.setOffscreenPageLimit(3);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messenger.requestPage.observeSend(this, states.page::setValue);
    }
}