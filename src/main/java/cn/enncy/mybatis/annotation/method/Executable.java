package cn.enncy.mybatis.annotation.method;

import cn.enncy.mybatis.annotation.type.Result;
import cn.enncy.mybatis.core.result.MapResultHandler;

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
    Class<? extends MapResultHandler> handler();
    Result[] resultMaps();
}
