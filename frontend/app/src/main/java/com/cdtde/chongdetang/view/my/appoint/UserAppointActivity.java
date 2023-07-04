package com.cdtde.chongdetang.view.my.appoint;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.recycler.AppointmentAdapter;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.BaseAdapter.ListHandlerFactory;
import com.cdtde.chongdetang.base.view.BindingConfig;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.MessageHolder;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActivityUserAppointBinding;
import com.cdtde.chongdetang.entity.Appointment;
import com.cdtde.chongdetang.requester.AppointRequester;
import com.cdtde.chongdetang.utils.DialogUtil;
import com.cdtde.chongdetang.utils.WindowUtil;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;


public class UserAppointActivity extends BaseActivity<ActivityUserAppointBinding> {

    @InjectScope(Scopes.APPLICATION)
    private AppointRequester appointRequester;

    @InjectScope(Scopes.ACTIVITY)
    private States states;

    @InjectScope(Scopes.ACTIVITY)
    private Messenger messenger;

    public static class States extends StateHolder {
        public final State<List<Appointment>> appointments = new State<>(new ArrayList<>());
    }

    public static class Messenger extends MessageHolder {
        public final Event<Appointment, Unit> infoEvent = new Event<>();
    }

    public class ListHandler extends ListHandlerFactory {
        public OnItemClickListener<Appointment> detail = (data, view) -> {
            messenger.infoEvent.send(data);
            DialogUtil.create(activity, AppointInfoDialog.class).show();
        };

        @Override
        public BindingConfig getBindingConfig() {
            return new BindingConfig().add(BR.detail, detail);
        }
    }

    @Override
    protected ViewConfig configBinding() {
        AppointmentAdapter adapter = new AppointmentAdapter();
        adapter.setFactory(new ListHandler());

        appointRequester.registerObserver(DialogUtil.createNetLoading(this), this);
        return new ViewConfig(R.layout.activity_user_appoint)
                .add(BR.state, states)
                .add(BR.adapter, adapter);
    }

    @Override
    protected void initUIComponent(@NonNull ActivityUserAppointBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appointRequester.updateAllAppointment(states.appointments::setValue, ToastUtils::showShort);
    }

}
