package com.cdtde.chongdetang.viewModel.shop;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.entity.Shopping;
import com.cdtde.chongdetang.repository.ShopRepository;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/20 18:55
 * @Version 1
 */
public class ShoppingViewModel extends ViewModel {

    public static class CheckedShopping extends BaseObservable {
        private boolean isChecked;
        private Shopping shopping;

        public CheckedShopping(boolean isChecked, Shopping shopping) {
            this.isChecked = isChecked;
            this.shopping = shopping;
        }

        @Bindable
        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
            notifyPropertyChanged(BR.checked);
        }

        @Bindable
        public Shopping getShopping() {
            return shopping;
        }

        public void setShopping(Shopping shopping) {
            this.shopping = shopping;
            notifyPropertyChanged(BR.shopping);
        }
    }

    private ShopRepository repo;

    private MutableLiveData<List<CheckedShopping>> checkedShoppings;

    private MutableLiveData<Integer> selectNumber;

    private MutableLiveData<Double> selectPrice;

    private MutableLiveData<Boolean> allSelect;

    public ShoppingViewModel() {
        repo = ShopRepository.getInstance();
        checkedShoppings = new MutableLiveData<>();
        allSelect = new MutableLiveData<>();
        selectNumber = new MutableLiveData<>(0);
        selectPrice = new MutableLiveData<>(0.0);

        updateShopping();
    }

    public MutableLiveData<List<CheckedShopping>> getCheckedShoppings() {
        return checkedShoppings;
    }

    public MutableLiveData<Integer> getSelectNumber() {
        return selectNumber;
    }

    public MutableLiveData<Double> getSelectPrice() {
        return selectPrice;
    }

    public MutableLiveData<Boolean> getAllSelect() {
        return allSelect;
    }

    public void updateShopping() {
        repo.requestShopping();
    }

    public void refreshShopping() {
        List<CheckedShopping> collect = repo.getShoppings().stream()
                .map(shopping -> new CheckedShopping(false, shopping))
                .collect(Collectors.toList());
        checkedShoppings.setValue(collect);
    }

    public void checkAll(boolean isChecked) {
        List<CheckedShopping> collection;
        if ((collection = checkedShoppings.getValue()) != null) {
            // 更新CheckBox状态
            allSelect.setValue(isChecked);
            collection.forEach(checkedShopping -> checkedShopping.setChecked(isChecked));
            // 更新价格
            // 防止多加，直接刷新计算
            refreshPrice();
            // 更新个数
            int select = isChecked ? collection.size() : 0;
            selectNumber.setValue(select);
        }
    }

    public boolean checkOne(CheckedShopping checkedShopping, boolean isChecked) {
        List<CheckedShopping> collection = checkedShoppings.getValue();
        Integer number = selectNumber.getValue();
        Double price = selectPrice.getValue();
        if (collection != null && number != null && price != null) {
            // 更新CheckBox状态
            checkedShopping.setChecked(isChecked);
            // 更新已选个数
            int i = isChecked ? 1 : -1;
            selectNumber.setValue(number + i);
            // 更新已选价格
            refreshPrice();

            return number + i == collection.size();
        }
        return false;
    }

    public boolean addProductNumber(CheckedShopping checkedShopping) {
        Shopping shopping = checkedShopping.getShopping();
        int n = shopping.getNumber() + 1;
        if (n > 100) {
            return true;
        }
        repo.requestUpdateShoppingNumber(shopping, n);
        return false;
    }

    public boolean subtractProductNumber(CheckedShopping checkedShopping) {
        Shopping shopping = checkedShopping.getShopping();
        int n = shopping.getNumber() - 1;
        if (n < 1) {
            return true;
        }
        repo.requestUpdateShoppingNumber(shopping, n);
        return false;
    }

    public void refreshPrice() {
        List<CheckedShopping> cs;
        if ((cs = checkedShoppings.getValue()) != null) {
            OptionalDouble allPrice = cs.stream()
                    .filter(CheckedShopping::isChecked)
                    .map(CheckedShopping::getShopping)
                    .mapToDouble(shopping -> shopping.getNumber() * shopping.getProduct().getPrice())
                    .reduce(Double::sum);
            double value = allPrice.orElse(0);
            selectPrice.setValue(value);
        }
    }

    public void deleteShopping(CheckedShopping checkedShopping) {
        Shopping shopping = checkedShopping.getShopping();
        repo.requestDeleteShopping(shopping);
    }
}
