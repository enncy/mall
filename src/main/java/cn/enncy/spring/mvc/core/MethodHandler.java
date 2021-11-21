package cn.enncy.spring.mvc.core;

import java.lang.annotation.*;

/**
 * //TODO
 * <br/>Created in 17:47 2021/11/20
 *
 * @author enncy
 */


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodHandler {
    Class<? extends Annotation> target();
    String type();
}
