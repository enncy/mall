package cn.enncy.mall.annotaion;

import cn.enncy.mall.constant.ServiceMapping;

import java.lang.annotation.*;

/**
 * 需要选择功能的属性
 * <br/>Created in 21:03 2021/11/23
 *
 * @author enncy
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Select {
    Option[] options() default {};

}
