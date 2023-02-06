package com.cdtde.chongdetang.controller;

import com.cdtde.chongdetang.pojo.Address;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/5 23:25
 * @Version 1
 */
@RestController
@RequestMapping("/api/user/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    public ResponseResult<List<Address>> getAllAddress() {
        return addressService.getAllAddress();
    }

    @PostMapping
    public ResponseResult<Address> updateAddress(@RequestBody Address address) {
        return addressService.updateAddress(address);
    }

}
