package cn.enncy.mybatis.annotation;

import cn.enncy.mybatis.core.result.ResultSetHandler;

import java.lang.annotation.*;

/**
 * 可执行的
 * <br/>Created in 23:25 2021/11/18
 *
 * @author enncy
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Executable {
    Class<? extends ResultSetHandler> handler();
    Class<?> target();
    Result[] resultMaps();
}
