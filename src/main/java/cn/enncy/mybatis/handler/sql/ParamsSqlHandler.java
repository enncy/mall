package cn.enncy.mybatis.handler.sql;


import cn.enncy.mybatis.annotation.param.Body;
import cn.enncy.mybatis.annotation.param.Param;
import cn.enncy.mybatis.annotation.type.Mapper;
import cn.enncy.mybatis.entity.MybatisException;

import cn.enncy.mybatis.entity.SQL;
import cn.enncy.mybatis.handler.param.BodyHandler;
import cn.enncy.mybatis.handler.param.ParamHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * //TODO
 * <br/>Created in 19:04 2021/11/19
 *
 * @author enncy
 */
public abstract  class ParamsSqlHandler extends TableSqlHandler {

    private static final BodyHandler BODY_HANDLER = new BodyHandler();
    private static final ParamHandler PARAM_HANDLER = new ParamHandler();

    public ParamsSqlHandler(Method method, Class<?> target, Mapper mapper, Object[] methodArguments) {
        super(method, target,mapper, methodArguments);
    }

    @Override
    public SQL handle(SQL sql) throws MybatisException {
        SQL handle = super.handle(sql);
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            // 通过 Body注解  处理
            if (parameter.isAnnotationPresent(Body.class)) {

                handle.setValue(BODY_HANDLER.handle(handle.getValue(), parameter, methodArguments[i]));
            }
            // 通过 Param注解  处理
            else if (parameter.isAnnotationPresent(Param.class)) {
                handle.setValue(PARAM_HANDLER.handle(handle.getValue(), parameter, methodArguments[i]));
            }else{
                throw new MybatisException("there is no handler to resolve param : " + parameter.getName() + " in sql : " + handle.getValue());
            }
        }
        return handle;
    }
}
