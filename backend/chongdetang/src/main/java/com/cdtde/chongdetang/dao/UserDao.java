package com.cdtde.chongdetang.dao;

import com.cdtde.chongdetang.bean.User;
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
public interface UserDao {
    @Select("select * from user where id=#{id};")
    User getById(Integer id);
}
