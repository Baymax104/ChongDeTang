package com.cdtde.chongdetang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdtde.chongdetang.pojo.Shopping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/20 19:58
 * @Version 1
 */
@Mapper
public interface ShoppingMapper extends BaseMapper<Shopping> {
    List<Shopping> getShoppingByUserId(@Param("userId") Integer userId);
}
