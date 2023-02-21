package com.cdtde.chongdetang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdtde.chongdetang.pojo.Shopping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

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
    List<Shopping> selectByOneKey(@Nullable @Param("userId") Integer userId,
                                  @Nullable @Param("productId") Integer productId);
    int insertShopping(@Param("shopping") Shopping shopping);
    Shopping selectByAllKey(@NonNull @Param("userId") Integer userId,
                            @NonNull @Param("productId") Integer productId);
}
