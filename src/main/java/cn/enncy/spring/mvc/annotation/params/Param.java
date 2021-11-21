package cn.enncy.spring.mvc.annotation.params;


import java.lang.annotation.*;

/**
 * //TODO
 * <br/>Created in 15:41 2021/11/20
 *
 * @author enncy
 */

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Param {
    String value() ;
}
