package com.cdtde.chongdetang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/6/19 21:41
 * @Version 1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserCollection {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Collection collection;
}
