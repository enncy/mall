package cn.enncy.mybatis.handler.param;


import cn.enncy.mybatis.annotation.param.Param;
import cn.enncy.mybatis.core.SqlStringHandler;

import java.lang.reflect.Parameter;

/**
 * //TODO
 * <br/>Created in 17:05 2021/11/6
 *
 * @author enncy
 */
public class ParamHandler implements Handler{

    @Override
    public  String handle(String sql, Parameter parameter, Object value){
        if(value instanceof Boolean){
            value = Boolean.parseBoolean(String.valueOf(value))?1:0;
        }


        Param param = parameter.getAnnotation(Param.class);
        sql = SqlStringHandler.replaceParam(sql, param.value(), String.valueOf(value));
        return sql;
    }
}
