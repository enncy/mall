package cn.enncy.mybatis.handler.param;


import cn.enncy.mybatis.core.ReflectUtils;
import cn.enncy.mybatis.core.SqlConstant;
import cn.enncy.mybatis.core.SqlStringHandler;
import cn.enncy.mybatis.entity.MybatisSqlError;

import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * //TODO
 * <br/>Created in 17:06 2021/11/6
 *
 * @author enncy
 */
public class BodyHandler implements Handler {


    @Override
    public String handle(String sql, Parameter parameter, Object value) throws MybatisSqlError {

        String result = sql;
        Map<String, Object> objectsValueMap = ReflectUtils.getObjectsValueMap(value);


        if (sql.toUpperCase().startsWith(SqlConstant.INSERT)) {
            objectsValueMap.remove("id");
            System.out.println(objectsValueMap);
            result = SqlStringHandler.replaceInsertFields(result, objectsValueMap);
        } else {
            if (sql.toUpperCase().startsWith(SqlConstant.UPDATE)) {
                Object id = objectsValueMap.remove("id");
                result = SqlStringHandler.replaceUpdateFields(result, objectsValueMap);
                objectsValueMap.put("id", id);
                result = SqlStringHandler.replaceParams(result, objectsValueMap);
            } else if(sql.toUpperCase().startsWith(SqlConstant.DELETE)){
                result = SqlStringHandler.replaceParams(result, objectsValueMap);
            }else{
                throw new MybatisSqlError("sql body handler error , no  insert,update or delete prefix . sql : "+sql+" , value : "+value);
            }

        }

        return result;
    }
}
