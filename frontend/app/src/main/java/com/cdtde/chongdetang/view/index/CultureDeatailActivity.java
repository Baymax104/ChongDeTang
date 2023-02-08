package com.cdtde.chongdetang.view.index;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityCultureDeatailBinding;
import com.cdtde.chongdetang.util.WebViewUtil;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.viewModel.CultureViewModel;

import io.reactivex.annotations.NonNull;

public class CultureDeatailActivity extends AppCompatActivity {
    private ActivityCultureDeatailBinding binding;
    private CultureViewModel vm;
    private String webUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_culture_deatail);
        binding.setLifecycleOwner(this);
        WindowUtil.initActivityWindow(binding.toolbar, this, true);

        WebViewUtil.configure(binding.webPage, false);

        vm = new ViewModelProvider(this).get(CultureViewModel.class);
        webUrl = vm.getDetailUel();
        binding.webPage.loadUrl(webUrl);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CultureDeatailActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
}