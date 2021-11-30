package cn.enncy.mall.annotaion;

import cn.enncy.mall.constant.ServiceMapping;

import java.lang.annotation.*;

/**
 * 需要引用功能的属性
 * <br/>Created in 22:16 2021/11/25
 *
 * @author enncy
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Reference {
    // 引用
    ServiceMapping  value();

    String key() default "id";
}
