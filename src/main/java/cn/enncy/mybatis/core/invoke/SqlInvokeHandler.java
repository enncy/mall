package cn.enncy.mybatis.core.invoke;

import cn.enncy.mall.utils.Logger;
import cn.enncy.mybatis.annotation.type.Mapper;
import cn.enncy.mybatis.core.DBUtils;
import cn.enncy.mybatis.core.result.*;
import cn.enncy.mybatis.entity.MybatisException;
import cn.enncy.mybatis.entity.SQL;
import cn.enncy.mybatis.handler.sql.DefaultSqlHandler;
import cn.enncy.mybatis.utils.ParameterizedTypeUtils;
import java.lang.reflect.*;
import java.sql.ResultSet;
import java.util.*;

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
        Mapper mapper ;
        // 查找 mapper
        if (target.isAnnotationPresent(Mapper.class)) {
            mapper = target.getAnnotation(Mapper.class);
        } else {
            mapper = findMapper();
        }
        // 直接执行
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
            DefaultResultHandler handler = new DefaultResultHandler(method, resultSet, targetType, mapper);
            // 处理返回结果
            return handler.handle();
        });
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
