package cn.enncy.mybatis.annotation.method;

import java.lang.annotation.*;

/**
 * //TODO
 * <br/>Created in 15:16 2021/11/6
 *
 * @author enncy
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Select {
    String[] value();
}
