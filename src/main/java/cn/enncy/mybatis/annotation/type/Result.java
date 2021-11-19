package cn.enncy.mybatis.annotation.type;

import java.lang.annotation.*;

/**
 * //TODO
 * <br/>Created in 0:10 2021/11/19
 *
 * @author enncy
 */

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Result {

    String key();
    Class<?> target();
}
