package com.cdtde.chongdetang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdtde.chongdetang.pojo.UserProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/6/19 22:17
 * @Version 1
 */
@Mapper
public interface UserProductMapper extends BaseMapper<UserProduct> {

    List<UserProduct> getUserProduct(@Param("userId") Integer userId);

    int insertUserProduct(@Param("userProduct") UserProduct userProduct);
}
