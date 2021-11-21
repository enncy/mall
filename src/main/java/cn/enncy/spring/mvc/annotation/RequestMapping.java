package cn.enncy.spring.mvc.annotation;

import java.lang.annotation.*;

/**
 * //TODO
 * <br/>Created in 16:49 2021/11/20
 *
 * @author enncy
 */

@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RequestMapping {
    String method();
    String value() default "";
}
