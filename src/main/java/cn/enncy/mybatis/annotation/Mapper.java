package cn.enncy.mybatis.annotation;

import java.lang.annotation.*;

/**
 * //TODO
 * <br/>Created in 15:40 2021/11/6
 *
 * @author enncy
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Mapper {
    String table();
    Class<?> target();
}