package cn.enncy.mybatis.handler.sql;


import cn.enncy.mybatis.annotation.method.Begin;
import cn.enncy.mybatis.entity.MybatisSqlError;
import cn.enncy.mybatis.entity.SQL;

import java.lang.reflect.Method;

/**
 * 事务处理器
 * <br/>Created in 20:39 2021/11/19
 *
 * @author enncy
 */
public class TransactionSqlHandler extends ParamsSqlHandler {


    public TransactionSqlHandler(Method method, Class<?> target, Object[] methodArguments) {
        super(method, target, methodArguments);
    }


    @Override
    public SQL handle(SQL sql) throws MybatisSqlError {
        SQL handle = super.handle(sql);
        if (method.isAnnotationPresent(Begin.class)) {
            String value = String.join("\n",
                    "begin;",
                    handle.getValue(),
                    "commit;"
            );
            handle.setValue(value);
        }
        return handle;
    }
}
