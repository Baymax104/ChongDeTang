package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.Address;
import com.cdtde.chongdetang.pojo.ResponseResult;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/5 23:25
 * @Version 1
 */
public interface AddressService {
    ResponseResult<List<Address>> getAllAddress();

    ResponseResult<Address> updateAddress(Address address);

    ResponseResult<Object> deleteAddress(Address address);
}
