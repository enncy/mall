package cn.enncy.spring.mvc.annotation;


import java.lang.annotation.*;

/**
 * //TODO
 * <br/>Created in 22:38 2021/11/25
 *
 * @author enncy
 */


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestController {
    String value() default "";
}
