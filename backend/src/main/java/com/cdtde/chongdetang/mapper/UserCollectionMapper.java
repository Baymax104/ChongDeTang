package com.cdtde.chongdetang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdtde.chongdetang.pojo.UserCollection;
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
public interface UserCollectionMapper extends BaseMapper<UserCollection> {
    List<UserCollection> getUserCollection(@Param("userId") Integer userId);

    int insertUserCollection(@Param("userCollection") UserCollection userCollection);

    List<UserCollection> getAllUserCollection();
}
