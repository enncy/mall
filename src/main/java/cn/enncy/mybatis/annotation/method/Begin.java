package cn.enncy.mybatis.annotation.method;

import java.lang.annotation.*;

/**
 * 开启事务
 * <br/>Created in 20:52 2021/11/19
 *
 * @author enncy
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Begin {
}
