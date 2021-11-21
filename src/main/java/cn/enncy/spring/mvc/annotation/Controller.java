package cn.enncy.spring.mvc.annotation;


import java.lang.annotation.*;

/**
 * //TODO
 * <br/>Created in 15:36 2021/11/20
 *
 * @author enncy
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
    String value() default "";
}
