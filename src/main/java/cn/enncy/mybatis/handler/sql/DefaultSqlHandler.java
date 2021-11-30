package cn.enncy.mybatis.handler.sql;


import cn.enncy.mybatis.annotation.method.Delete;
import cn.enncy.mybatis.annotation.method.Insert;
import cn.enncy.mybatis.annotation.method.Select;
import cn.enncy.mybatis.annotation.method.Update;
import cn.enncy.mybatis.annotation.type.Mapper;
import cn.enncy.mybatis.entity.MybatisException;

import cn.enncy.mybatis.entity.SQL;

import java.lang.reflect.Method;

/**
 * //TODO
 * <br/>Created in 20:55 2021/11/19
 *
 * @author enncy
 */
public class DefaultSqlHandler  extends ParamsSqlHandler{


    public DefaultSqlHandler(Method method, Class<?> target,Mapper mapper, Object[] methodArguments) {
        super(method, target,mapper, methodArguments);
    }

    public SQL handle() throws MybatisException {
        SQL sql = null;
        if (target.isAnnotationPresent(Mapper.class)) {
            if (method.isAnnotationPresent(Insert.class)) {
                sql = new SQL(String.join("\n",method.getAnnotation(Insert.class).value()), false);
            } else if (method.isAnnotationPresent(Update.class)) {
                sql = new SQL(String.join("\n",method.getAnnotation(Update.class).value()), false);
            } else if (method.isAnnotationPresent(Delete.class)) {
                sql = new SQL(String.join("\n", method.getAnnotation(Delete.class).value()), false);
            } else if (method.isAnnotationPresent(Select.class)) {
                sql =  new SQL(String.join("\n",method.getAnnotation(Select.class).value()), true);
            }
        }
        return super.handle(sql);
    }

    @Override
    public SQL handle(SQL sql) throws MybatisException {
        return super.handle(sql);
    }
}
