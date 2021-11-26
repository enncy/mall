package cn.enncy.mall.annotaion;


import cn.enncy.mall.constant.InputType;
import cn.enncy.mall.constant.ServiceMapping;
import cn.enncy.mall.constant.Tag;
import cn.enncy.mall.utils.formatter.DefaultFormatter;
import cn.enncy.mall.utils.formatter.Formatter;

import java.lang.annotation.*;

/**
 * 字段注解
 * <br/>Created in 21:28 2021/11/18
 *
 * @author enncy
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Info {
    // 注释
    String value();
    // 类型
    InputType type()  default InputType.TEXT;
    // 标签
    Tag tag() default Tag.INPUT;
    // 格式化类
    Class<? extends Formatter> formatter() default DefaultFormatter.class;
    // 是否禁用
    boolean disabled() default  false;
    // 显示级别
    int rank() default 0;

}
