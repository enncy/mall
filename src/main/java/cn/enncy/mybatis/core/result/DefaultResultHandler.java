package cn.enncy.mybatis.core.result;


import cn.enncy.mybatis.annotation.method.Executable;
import cn.enncy.mybatis.annotation.type.Mapper;
import cn.enncy.mybatis.annotation.type.Result;
import cn.enncy.mybatis.core.ReflectUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * //TODO
 * <br/>Created in 21:22 2021/12/5
 *
 * @author enncy
 */
public class DefaultResultHandler  implements ResultSetHandler {

    Method method;
    ResultSet resultSet;
    Class<?> targetType;
    Mapper mapper;

    public DefaultResultHandler(Method method, ResultSet resultSet, Class<?> targetType, Mapper mapper) {
        this.method = method;
        this.resultSet = resultSet;
        this.targetType = targetType;
        this.mapper = mapper;
    }

    public ResultSetHandler createHandler(){
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

    @Override
    public Object handle() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return this.createHandler().handle();
    }
}
