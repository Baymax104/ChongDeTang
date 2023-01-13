package com.cdtde.chongdetang.view.index;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityMomentBinding;
import com.cdtde.chongdetang.util.WebViewUtil;
import com.cdtde.chongdetang.util.WindowUtil;

public class MomentActivity extends AppCompatActivity {

    private ActivityMomentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_moment);
        binding.setLifecycleOwner(this);
        WindowUtil.initActivityWindow(binding.toolbar, this, true);

        WebViewUtil.configure(binding.webPage, true);

        binding.webPage.loadUrl("http://cdtde.com/m/list.php?tid=3");
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MomentActivity.class);
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

    private void hideHtmlContent() {
        //修改文本，可以注入任意的html代码，我是直接注入一段带样式的文本节点，表现良好

    }
}