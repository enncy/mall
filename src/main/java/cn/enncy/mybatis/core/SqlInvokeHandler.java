package cn.enncy.mybatis.core;


import cn.enncy.mybatis.annotation.*;
import cn.enncy.mybatis.annotation.Executable;
import cn.enncy.mybatis.core.result.ListResultHandler;
import cn.enncy.mybatis.core.result.ObjectResultHandler;
import cn.enncy.mybatis.core.result.ResultSetHandler;
import cn.enncy.mybatis.entity.SQL;
import cn.enncy.mybatis.handler.BodyHandler;
import cn.enncy.mybatis.handler.ParamHandler;

import java.lang.reflect.*;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;

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
        if (sql != null) {
            long l = System.currentTimeMillis();
            System.out.println("============[ sql execute ]============");
            System.out.println("\t" + sql.getValue());

            Object execute = execute(method, sql.getValue(), sql.isExecuteQuery());
            System.out.println("\t" + execute);
            System.out.println("============[ takes " + (System.currentTimeMillis() - l) + "/ms]============");
            return execute;
        }

        return null;
    }

    public Object execute(Method method, String sql, boolean execQuery) throws ClassNotFoundException {
        Class<?> returnType = method.getReturnType();
        Mapper mapper = target.getAnnotation(Mapper.class);
        if (execQuery) {

            return DBUtils.connect(statement -> {
                ResultSet resultSet = statement.executeQuery(sql);
                // 1. 获取需要转换的类型，如果没有 Executable 注解，则获取 Mapper 注解上的类型
                Class<?> target;
                // 2. 获取结果映射表, 如果没有 Executable 注解，则 根据返回类型判断
                Map<String, Class<?>> resultMap;
                // 3. 分配给处理器处理结果集
                ResultSetHandler handler;

                // 4. 如何方法多加了 Executable 属性，则按照 Executable 方法去执行
                if (method.isAnnotationPresent(Executable.class)) {
                    Executable executable = method.getAnnotation(Executable.class);
                    resultMap = new LinkedHashMap<>();
                    target = executable.target();
                    handler = executable.handler().getConstructor().newInstance();
                    Result[] results = executable.resultMaps();
                    for (Result result : results) {
                        resultMap.put(result.key(), result.target());
                    }
                } else {
                    target = mapper.target();
                    resultMap = Arrays.stream(target.getDeclaredFields()).collect(Collectors.toMap(Field::getName, Field::getType));
                    if (returnType.equals(List.class)) {
                        handler = new ListResultHandler();
                    } else {
                        handler = new ObjectResultHandler();
                    }
                }

                return handler.handle(resultSet, resultMap, target);
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

            return new SQL(sql, execQuery);
        }

        return null;
    }

}
