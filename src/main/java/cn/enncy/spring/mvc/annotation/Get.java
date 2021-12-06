package cn.enncy.spring.mvc.annotation;

import java.lang.annotation.*;

/**
 * //TODO
 * <br/>Created in 15:37 2021/11/20
 *
 * @author enncy
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Get {
    String value();
}
