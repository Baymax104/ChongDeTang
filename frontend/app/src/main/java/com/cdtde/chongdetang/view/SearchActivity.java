package com.cdtde.chongdetang.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivitySearchBinding;
import com.cdtde.chongdetang.viewModel.SearchViewModel;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;


    private SearchViewModel vm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());
        vm = new ViewModelProvider(this).get(SearchViewModel.class);
        binding.setViewModel(vm);
        init();


        // 最近搜索tags
        binding.historyFlow.setOnTagClickListener((view1, i, flowLayout) -> {
            binding.searchEdit.setText(vm.getHistoryTags().getValue().get(i));
            return true;
        });


        // 藏品推荐tags
        binding.collectionFlow.setOnTagClickListener((view1, i, flowLayout) -> {
            binding.searchEdit.setText(vm.getCollectionTags().getValue().get(i));
            return true;
        });

        // 商品推荐tags
        binding.productFlow.setOnTagClickListener((view1, i, flowLayout) -> {
            binding.searchEdit.setText(vm.getProductTags().getValue().get(i));
            return true;
        });

        // 搜索按钮
        binding.searchBtn.setOnClickListener(v -> {
            String content = binding.searchEdit.getText().toString();
            vm.search(content);
        });
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    private void init() {
        setSupportActionBar(binding.toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }
}