package cn.enncy.mybatis.annotation.method;


import java.lang.annotation.*;

/**
 * //TODO
 * <br/>Created in 15:17 2021/11/6
 *
 * @author enncy
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Delete {

    String[] value();
}
