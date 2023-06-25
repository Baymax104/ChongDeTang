package com.cdtde.chongdetang.base.vm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/6/23 15:28
 * @Version 1
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectScope {
    Scopes value();
}
