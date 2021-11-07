package cn.enncy.mybatis.handler;


import cn.enncy.mybatis.entity.MybatisSqlError;

import java.lang.reflect.Parameter;

/**
 * //TODO
 * <br/>Created in 17:06 2021/11/6
 *
 * @author enncy
 */
public interface Handler {
    String handle(String sql, Parameter parameter, Object value) throws MybatisSqlError;
}
