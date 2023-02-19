package com.cdtde.chongdetang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cdtde.chongdetang.mapper.AddressMapper;
import com.cdtde.chongdetang.pojo.Address;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.service.AddressService;
import com.cdtde.chongdetang.service.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/5 23:27
 * @Version 1
 */

@Slf4j
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public ResponseResult<List<Address>> getAllAddress() {
        ResponseResult<List<Address>> result = new ResponseResult<>();
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = loginUser.getUser().getId();

        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", id);
        List<Address> addresses = addressMapper.selectList(wrapper);

        result.setStatus("success").setData(addresses);
        return result;
    }

    @Override
    public ResponseResult<Address> updateAddress(Address address) {
        ResponseResult<Address> result = new ResponseResult<>();
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = loginUser.getUser().getId();

        // 新添加的地址
        if (address.getId() == null && address.getUserId() == null) {
            address.setUserId(id);
            int insert = addressMapper.insert(address);
            if (insert != 1) {
                throw new RuntimeException("添加地址失败");
            }
        } else {
            if (id != address.getUserId()) {
                throw new RuntimeException("用户信息错误");
            }

            // 修改前判重
            Address dbAddress = addressMapper.selectById(address.getId());
            if (address.equals(dbAddress)) {
                result.setStatus("success").setData(address);
                return result;
            }

            // 有变化才修改
            UpdateWrapper<Address> wrapper = new UpdateWrapper<>();
            wrapper.eq("id", address.getId())
                    .eq("user_id", address.getUserId());
            int update = addressMapper.update(address, wrapper);
            if (update != 1) {
                throw new RuntimeException("修改地址信息失败");
            }
        }

        result.setStatus("success").setData(address);
        return result;
    }

    @Override
    public ResponseResult<Object> deleteAddress(Address address) {
        ResponseResult<Object> result = new ResponseResult<>();

        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = loginUser.getUser().getId();

        if (id != address.getUserId()) {
            throw new RuntimeException("用户信息错误");
        }

        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.eq("id", address.getId())
                .eq("user_id", address.getUserId());
        int delete = addressMapper.delete(wrapper);
        if (delete != 1) {
            throw new RuntimeException("删除地址失败");
        }

        result.setStatus("success");
        return result;
    }
}
