package cn.enncy.mybatis.handler.sql;

import cn.enncy.mybatis.annotation.type.Mapper;
import cn.enncy.mybatis.core.SqlStringHandler;
import cn.enncy.mybatis.entity.MybatisException;

import cn.enncy.mybatis.entity.SQL;

import java.lang.reflect.Method;


/**
 * //TODO
 * <br/>Created in 18:54 2021/11/19
 *
 * @author enncy
 */
public abstract class TableSqlHandler implements SqlHandler {
    protected Method method;
    protected Class<?> target;
    protected Object[] methodArguments;
    protected Mapper mapper;

    public TableSqlHandler(Method method, Class<?> target,Mapper mapper, Object[] methodArguments) {
        this.method = method;
        this.target = target;
        this.methodArguments = methodArguments;
        this.mapper = mapper;
    }

    @Override
    public SQL handle(SQL sql) throws MybatisException {

        if(mapper==null){
            throw new MybatisException("annotation is not found : " + Mapper.class.getName());
        }

        sql.setValue(SqlStringHandler.replaceTableName(sql.getValue(), mapper.table()));
        return sql;
    }
}
