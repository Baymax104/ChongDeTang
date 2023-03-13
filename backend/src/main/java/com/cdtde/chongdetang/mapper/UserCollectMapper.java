package com.cdtde.chongdetang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdtde.chongdetang.pojo.UserCollect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/24 23:18
 * @Version 1
 */
@Mapper
public interface UserCollectMapper extends BaseMapper<UserCollect> {
    List<UserCollect> getUserCollection(@Param("userId") Integer userId);

    List<UserCollect> getUserProduct(@Param("userId") Integer userId);

    int insertUserCollect(@Param("userCollect") UserCollect userCollect);
}
