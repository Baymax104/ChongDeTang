package com.cdtde.chongdetang.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.cdtde.chongdetang.databinding.ActivitySearchBinding;
import com.cdtde.chongdetang.util.SearchTagAdapter;
import com.cdtde.chongdetang.viewModel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;


    private SearchViewModel vm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initView();
        vm = new ViewModelProvider(this).get(SearchViewModel.class);
        vm.getHistoryTags().observe(this, strings -> {
            SearchTagAdapter adapter = new SearchTagAdapter(this, strings);
            binding.searchHistoryFlow.setAdapter(adapter);
        });

        // 搜索框内容监听
        binding.searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString();
                if (content.equals("")) {
                    binding.searchCleanBtn.setVisibility(View.INVISIBLE);
                } else {
                    binding.searchCleanBtn.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.searchHistoryFlow.setOnTagClickListener((view1, i, flowLayout) -> {
            List<String> value = vm.getHistoryTags().getValue();
            if (value != null) {
                binding.searchEdit.setText(value.get(i));
            }
            return true;
        });

        // 搜索框清除按钮
        binding.searchCleanBtn.setOnClickListener(v -> binding.searchEdit.setText(""));

        // 藏品推荐tags
        List<String> collectionTags = vm.getCollectionTags();
        SearchTagAdapter collectionAdapter = new SearchTagAdapter(this, collectionTags);
        binding.searchCollectionFlow.setAdapter(collectionAdapter);
        binding.searchCollectionFlow.setOnTagClickListener((view1, i, flowLayout) -> {
            binding.searchEdit.setText(collectionTags.get(i));
            return true;
        });

        // 商品推荐tags
        List<String> productTags = vm.getProductTags();
        SearchTagAdapter productAdapter = new SearchTagAdapter(this, productTags);
        binding.searchProductFlow.setAdapter(productAdapter);
        binding.searchProductFlow.setOnTagClickListener((view1, i, flowLayout) -> {
            binding.searchEdit.setText(productTags.get(i));
            return true;
        });

        // 删除最近搜索按钮
        binding.searchDeleteBtn.setOnClickListener(v -> {
            binding.searchHistoryLabel.setVisibility(View.GONE);
            binding.searchHistoryFlow.setVisibility(View.GONE);
            vm.clearTag();
        });

        // 搜索按钮
        binding.searchBtn.setOnClickListener(v -> {
            String content = binding.searchEdit.getText().toString();
            if (!content.equals("")) {
                if (vm.isHistoryEmpty()) {
                    binding.searchHistoryFlow.setVisibility(View.VISIBLE);
                    binding.searchHistoryLabel.setVisibility(View.VISIBLE);
                }
                if (!vm.isHistoryExist(content)) {
                    vm.addTag(content);
                }
                // 搜索
            }
        });
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        setSupportActionBar(binding.searchToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }
}