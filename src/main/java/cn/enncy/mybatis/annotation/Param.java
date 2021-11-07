package cn.enncy.mybatis.annotation;

import java.lang.annotation.*;

/**
 * //TODO
 * <br/>Created in 15:20 2021/11/6
 *
 * @author enncy
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface Param {
    String value();
}
