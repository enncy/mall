package cn.enncy.mybatis.handler.param;



import cn.enncy.mybatis.entity.MybatisException;

import java.lang.reflect.Parameter;

/**
 * //TODO
 * <br/>Created in 17:06 2021/11/6
 *
 * @author enncy
 */
public interface Handler {
    String handle(String sql, Parameter parameter, Object value) throws MybatisException;
}
