package com.cdtde.chongdetang.mapper;

import com.cdtde.chongdetang.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/29 16:05
 * @Version 1
 */
@Mapper
public interface UserMapper {
    @Select("select * from user where id=#{id};")
    User getById(Integer id);
}
