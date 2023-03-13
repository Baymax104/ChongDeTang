package com.cdtde.chongdetang.view.shop;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.DialogCollectMenuBinding;
import com.cdtde.chongdetang.entity.UserCollect;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.core.AttachPopupView;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/3/13 13:32
 * @Version 1
 */
public class ItemCollectDialog extends AttachPopupView {

    private UserCollect userCollect;

    private boolean isCollect;

    public ItemCollectDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_collect_menu;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        View view = getPopupImplView();
        DialogCollectMenuBinding binding = DialogCollectMenuBinding.bind(view);
        binding.setLifecycleOwner(this);
        binding.setIsCollect(isCollect);

        LiveEventBus.get("ItemCollectDialog-show", UserCollect.class)
                .observeSticky(this, userCollect -> {
                    this.userCollect = userCollect;
                    isCollect =
                            (userCollect.getProduct() != null && userCollect.getProduct().isUserCollect()) ||
                                    (userCollect.getCollection() != null && userCollect.getCollection().isUserCollect());
                    binding.setIsCollect(isCollect);
                });

        LiveEventBus.get("ItemCollectDialog-refreshAddCollect", Boolean.class)
                .observe(this, refresh -> {
                    if (refresh) {
                        isCollect = true;
                        binding.setIsCollect(true);
                    }
                });
        LiveEventBus.get("ItemCollectDialog-refreshRemoveCollect", Boolean.class)
                .observe(this, refresh -> {
                    if (refresh) {
                        isCollect = false;
                        binding.setIsCollect(false);
                    }
                });

        view.setOnClickListener(v -> {
            if (isCollect) { // 已收藏，取消
                LiveEventBus.get("ItemCollectDialog-cancelCollect", UserCollect.class)
                        .post(userCollect);
            } else { // 未收藏，收藏
                LiveEventBus.get("ItemCollectDialog-collect", UserCollect.class)
                        .post(userCollect);
            }
        });
    }
}
