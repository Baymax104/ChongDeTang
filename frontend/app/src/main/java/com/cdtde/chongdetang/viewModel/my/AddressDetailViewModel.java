package com.cdtde.chongdetang.viewModel.my;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.entity.Address;
import com.cdtde.chongdetang.repository.MyRepository;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/6 0:35
 * @Version 1
 */
public class AddressDetailViewModel extends ViewModel {

    private MyRepository repo;
    private MutableLiveData<Address> address;

    private MutableLiveData<String> area;

    private String consignee;

    private String phone;

    private String province;

    private String city;

    private String detail;

    public AddressDetailViewModel() {
        repo = MyRepository.getInstance();
        address = new MutableLiveData<>();
        area = new MutableLiveData<>();
    }

    public void setAddress(Address address) {
        this.address.setValue(address);
    }

    public MutableLiveData<Address> getAddress() {
        return address;
    }

    public void setArea(String province, String city) {
        this.province = province;
        this.city = city;
        Address value = address.getValue();
        if (value != null) {
            value.setProvince(province).setCity(city);
        } else {
            area.setValue(province + " " + city);
        }
    }

    public MutableLiveData<String> getArea() {
        return area;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void updateAddress() {
        Address current = address.getValue();
        if (current == null) {
            current = new Address(province, city, detail, consignee, phone);
        } else {
            current.setConsignee(consignee)
                    .setPhone(phone)
                    .setProvince(province)
                    .setCity(city)
                    .setDetail(detail);
        }
        repo.updateAddress(current);
    }

    public void deleteAddress() {
        if (address.getValue() != null) {
            repo.deleteAddress(address.getValue());
        }
    }
}
