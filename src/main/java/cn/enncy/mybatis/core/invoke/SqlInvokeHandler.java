package cn.enncy.mybatis.core.invoke;


import cn.enncy.mall.mapper.UserMapper;
import cn.enncy.mall.utils.Logger;
import cn.enncy.mybatis.annotation.method.Executable;
import cn.enncy.mybatis.annotation.type.Mapper;
import cn.enncy.mybatis.annotation.type.Result;
import cn.enncy.mybatis.core.DBUtils;
import cn.enncy.mybatis.core.ReflectUtils;
import cn.enncy.mybatis.core.result.*;
import cn.enncy.mybatis.entity.MybatisException;
import cn.enncy.mybatis.entity.SQL;

import cn.enncy.mybatis.handler.sql.DefaultSqlHandler;
import cn.enncy.mybatis.utils.ParameterizedTypeUtils;

import java.lang.reflect.*;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public SqlInvokeHandler(Class<?> target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws MybatisException {
        //System.out.println("method "+method);
        //System.out.println("mapper "+mapper);
        Mapper mapper ;
        if (target.isAnnotationPresent(Mapper.class)) {
            mapper = target.getAnnotation(Mapper.class);
        } else {
            mapper = findMapper();
        }

        if (mapper == null) {
            try {
                return method.invoke(proxy, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new MybatisException(e.getMessage());
            }
        } else {

            Logger.log("-------------[ sql execute ]--------------");
            // 1. 获取需要转换的类型
            Class<?> targetType = resolveTargetType(method, mapper);

            // 2. 处理语句
            SQL sql = new DefaultSqlHandler(method, target, mapper, args).handle();

            if (sql != null) {
                long time = 0;
                Object value;
                // 3. 执行语句
                if (sql.isExecuteQuery()) {
                    value = executeQuery(method, targetType, sql.getValue(), mapper);
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

    }

    // 执行 sql
    public Object execute(String sql) throws MybatisException {
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
    public Object executeQuery(Method method, Class<?> targetType, String sql, Mapper mapper) throws MybatisException {
        return DBUtils.connect(statement -> {
            long l = System.currentTimeMillis();
            // 结果集
            ResultSet resultSet = statement.executeQuery(sql);
            Logger.log("\ttakes\t: " + (System.currentTimeMillis() - l) + "/ms");
            //  处理器处理结果集
            ResultSetHandler handler = resolveResultSetHandler(method, resultSet, targetType, mapper);
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
    public ResultSetHandler resolveResultSetHandler(Method method, ResultSet resultSet, Class<?> targetType, Mapper mapper) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //  获取结果映射表
        Map<String, Class<?>> resultMap;
        // 如何方法多加了 Executable 属性，则按照 Executable 的结果集映射去执行
        if (method.isAnnotationPresent(Executable.class)) {
            Executable executable = method.getAnnotation(Executable.class);
            resultMap = Arrays.stream(executable.resultMaps()).collect(Collectors.toMap(Result::key, Result::target));
            if(executable.singleResult()){
                return new SingleResultHandler(resultSet, resultMap);
            }
        } else {
            resultMap = ReflectUtils.getObjectFieldsTypeMap(mapper.target());
        }

        // 如果返回值为 list 对象
        if (method.getReturnType().equals(List.class)) {
            // 集合处理器
            return new ListResultHandler(resultSet, targetType, resultMap);
        } else if(method.getReturnType().equals(Map.class)){
            return new MapResultHandler(resultSet, resultMap);
        }   else{
            // 对象处理器
            return new ObjectResultHandler(resultSet, targetType, resultMap);
        }
    }


    /**
     * 获取执行的目标模型类型，例如 User.class
     *
     * @param method 代理的对象方法
     * @return java.lang.Class<?>
     */
    public Class<?> resolveTargetType(Method method, Mapper mapper) {
        Class<?> targetType;
        Type genericReturnType = method.getGenericReturnType();
        if(genericReturnType instanceof ParameterizedType){
            Type parameterizedType = ParameterizedTypeUtils.get(genericReturnType, 0);

            // 如果是列表
            if (List.class.isAssignableFrom(genericReturnType.getClass())) {
                // 获取列表中的泛型
                targetType = mapper.target();
            } else {
                if (parameterizedType instanceof ParameterizedType) {
                    targetType = (Class<?>) ((ParameterizedType) parameterizedType).getRawType();
                } else  {
                    targetType = mapper.target();
                }
            }
        }else{
            targetType = mapper.target();
        }

        return targetType;
    }


    public Mapper findMapper(){
        Mapper mapper = null;
        LinkedList<Class<?>> interfaceList = new LinkedList<>();
        interfaceList.add(target);
        interfaceList.add(target.getSuperclass());
        interfaceList.addAll(Arrays.asList(target.getInterfaces()));
        while (!interfaceList.isEmpty()) {
            Class<?> first = interfaceList.pop();
            if (first != null) {
                if (first.isAnnotationPresent(Mapper.class)) {
                    mapper = first.getAnnotation(Mapper.class);
                    break;
                } else {
                    interfaceList.push(first.getSuperclass());
                    interfaceList.addAll(Arrays.asList(first.getInterfaces()));
                }
            }
        }
        return mapper;
    }

}
