package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.Address;
import com.cdtde.chongdetang.pojo.Result;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/5 23:25
 * @Version 1
 */
public interface AddressService {

    /**
     * 获取所有地址
     * @return Address列表
     */
    Result<List<Address>> getAllAddress();

    Result<Address> updateAddress(Address address);

    Result<Object> deleteAddress(Address address);
}
