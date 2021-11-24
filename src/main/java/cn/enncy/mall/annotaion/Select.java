package cn.enncy.mall.annotaion;

import java.lang.annotation.*;

/**
 * //TODO
 * <br/>Created in 21:03 2021/11/23
 *
 * @author enncy
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Select {
    Option[] options();
}
