package cn.enncy.mybatis.core;


import cn.enncy.mall.utils.Logger;
import cn.enncy.mybatis.annotation.method.Executable;
import cn.enncy.mybatis.annotation.type.Mapper;
import cn.enncy.mybatis.annotation.type.Result;
import cn.enncy.mybatis.core.result.ListResultHandler;
import cn.enncy.mybatis.core.result.ObjectResultHandler;
import cn.enncy.mybatis.core.result.ResultSetHandler;
import cn.enncy.mybatis.entity.SQL;
import cn.enncy.mybatis.handler.param.BodyHandler;
import cn.enncy.mybatis.handler.param.ParamHandler;
import cn.enncy.mybatis.handler.sql.DefaultSqlHandler;
import cn.enncy.mybatis.utils.ParameterizedTypeUtils;

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
        Logger.log("-------------[ sql execute ]--------------");
        // 1. 获取需要转换的类型
        Class<?> targetType = resolveTargetType(proxy, method);
        // 2. 处理语句
        SQL sql = new DefaultSqlHandler(method, target, args).handle();

        if (sql != null) {
            long time = 0;
            Object value;
            // 3. 执行语句
            if (sql.isExecuteQuery()) {
                value = executeQuery(method, targetType, sql.getValue());
            } else {
                long l = System.currentTimeMillis();
                value = execute(sql.getValue());
                time = System.currentTimeMillis() - l;
            }

            Logger.log(
                    "\tsql\t: " + sql.getValue(),
                    "\tresult\t: " + value,
                    (time == 0 ? "" : "\ttakes\t: " + time + "/ms")
            );
            return value;
        }

        return null;
    }

    // 执行 sql
    public Object execute(String sql) throws ClassNotFoundException {
        return DBUtils.connect(statement -> {
            String[] split = sql.split("\n");
            if (split.length != 0) {
                for (String s : split) {
                    statement.execute(s);
                }
                return true;
            } else {
                return statement.execute(sql);
            }

        });
    }

    // 执行查询语句
    public Object executeQuery(Method method, Class<?> targetType, String sql) throws ClassNotFoundException {
        return DBUtils.connect(statement -> {
            long l = System.currentTimeMillis();
            // 结果集
            ResultSet resultSet = statement.executeQuery(sql);
            Logger.log("\ttakes\t: " + (System.currentTimeMillis() - l) + "/ms");
            //  处理器处理结果集
            ResultSetHandler handler = resolveResultSetHandler(method, resultSet, targetType);
            // 处理返回结果
            return handler.handle();
        });
    }

    /**
     * 获取处理器
     *
     * @param method     代理方法
     * @param resultSet  sql结果集
     * @param targetType 目标类型
     * @return cn.enncy.mybatis.core.result.ResultSetHandler
     */
    public ResultSetHandler resolveResultSetHandler(Method method, ResultSet resultSet, Class<?> targetType) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // 如何方法多加了 Executable 属性，则按照 Executable 方法去执行
        if (method.isAnnotationPresent(Executable.class)) {
            Executable executable = method.getAnnotation(Executable.class);
            //  获取结果映射表
            Map<String, Class<?>> resultMap = Arrays.stream(executable.resultMaps()).collect(Collectors.toMap(Result::key, Result::target));
            // map 映射处理器
            return executable.handler().getConstructor(ResultSet.class, Map.class).newInstance(resultSet, resultMap);
        } else {
            // 如果返回值为 list 对象
            if (method.getReturnType().equals(List.class)) {
                // 集合处理器
                return new ListResultHandler(resultSet, targetType);
            } else {
                // 对象处理器
                return new ObjectResultHandler(resultSet, targetType);
            }
        }
    }


    /**
     * 获取执行的目标模型类型，例如 User.class
     *
     * @param proxy  代理对象
     * @param method 代理的对象方法
     * @return java.lang.Class<?>
     */
    public Class<?> resolveTargetType(Object proxy, Method method) {
        Class<?> targetType;
        Class<?> returnType = method.getReturnType();
        // 如果是列表
        if (returnType.equals(List.class)) {
            Mapper mapper = target.getAnnotation(Mapper.class);
            // 获取列表中的泛型
            targetType = mapper.target();
        } else {
            // 如何返回值是泛型
            if (ParameterizedType.class.isAssignableFrom(returnType)) {
                // 获取类的泛型作为目标类型
                targetType = ParameterizedTypeUtils.get(proxy.getClass(), 0);
            } else {
                // 获取返回值作为目标类型
                targetType = returnType;
            }
        }
        return targetType;
    }

}
