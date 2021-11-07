package cn.enncy.mybatis.core;


import cn.enncy.mybatis.annotation.*;
import cn.enncy.mybatis.entity.SQL;
import cn.enncy.mybatis.handler.BodyHandler;
import cn.enncy.mybatis.handler.ParamHandler;

import java.lang.reflect.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * //TODO
 * <br/>Created in 15:22 2021/11/6
 *
 * @author enncy
 */
public class SqlInvokeHandler implements InvocationHandler {

    public Class<?> target;
    BodyHandler bodyHandler = new BodyHandler();
    ParamHandler paramHandler = new ParamHandler();

    public SqlInvokeHandler(Class<?> target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        SQL sql = resolveSql(method, args);
        if(sql!=null){
            long l = System.currentTimeMillis();
            System.out.println("============[ sql execute ]============");
            System.out.println("\t"+sql.getValue());

            Object execute = execute(method, sql.getValue(), sql.isExecuteQuery());
            System.out.println("\t"+execute);
            System.out.println("============[ takes "+(System.currentTimeMillis() - l)+"/ms]============");
            return execute;
        }



        return method.invoke(proxy,args);
    }

    public Object execute(Method method, String sql, boolean execQuery) throws ClassNotFoundException {
        Class<?> returnType = method.getReturnType();
        Mapper mapper = target.getAnnotation(Mapper.class);
        if (execQuery) {

            return DBUtils.connect(statement -> {
                ResultSet resultSet = statement.executeQuery(sql);
                if (returnType.isInstance(List.class) || returnType.equals(List.class)) {
                    // 目标转换类型根据 resultType 字段 sql 注解的优先级最高，其次 到mapper 的注解
                    List<Object> list = new ArrayList<>();
                    while (resultSet.next()) {
                        Object resultTarget = ResultSetHandler.createResultTarget(resultSet, mapper.target());
                        list.add(resultTarget);
                    }
                    return list;
                } else {
                    if (resultSet.next()) {
                        return ResultSetHandler.createResultTarget(resultSet, returnType);
                    }
                }
                return null;
            });
        } else {
            return DBUtils.connect(statement -> statement.execute(sql));
        }
    }

    public SQL resolveSql(Method method, Object[] args) throws Exception {
        if (target.isAnnotationPresent(Mapper.class)) {
            Mapper mapper = target.getAnnotation(Mapper.class);
            boolean execQuery = false;

            String sql = "";
            if (method.isAnnotationPresent(Insert.class)) {
                sql = method.getAnnotation(Insert.class).value();
            } else if (method.isAnnotationPresent(Update.class)) {
                sql = method.getAnnotation(Update.class).value();
            } else if (method.isAnnotationPresent(Delete.class)) {
                sql = method.getAnnotation(Delete.class).value();
            } else if (method.isAnnotationPresent(Select.class)) {
                execQuery = true;
                sql = method.getAnnotation(Select.class).value();
            }

            // 处理表名
            sql = SqlStringHandler.replaceTableName(sql, mapper.table());

            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                // 通过 Body注解  处理
                if (parameter.isAnnotationPresent(Body.class)) {
                    sql = bodyHandler.handle(sql, parameter, args[i]);
                }
                // 通过 Param注解  处理
                else if (parameter.isAnnotationPresent(Param.class)) {
                    sql = paramHandler.handle(sql, parameter, args[i]);
                }
                //如果参数都没有以上的注解，则抛出异常
                else {
                    throw new Exception("The method's parameters  is not match  those annotation:(Body|Param)");
                }
            }

            return new SQL(sql,execQuery);
        }

        return null;
    }

}
