package cn.enncy.mall.annotaion;

import java.lang.annotation.*;

/**
 * //TODO
 * <br/>Created in 21:04 2021/11/23
 *
 * @author enncy
 */

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Option {
    String value();
    String description();
}
