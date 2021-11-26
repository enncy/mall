package cn.enncy.mall.annotaion;

import java.lang.annotation.*;

/**
 * 可展示的属性
 * <br/>Created in 18:59 2021/11/26
 *
 * @author enncy
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Showable {
}
