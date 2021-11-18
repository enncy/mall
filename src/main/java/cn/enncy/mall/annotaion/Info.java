package cn.enncy.mall.annotaion;


import java.lang.annotation.*;

/**
 * 字段注解
 * <br/>Created in 21:28 2021/11/18
 *
 * @author enncy
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Info {
    String value();


}
