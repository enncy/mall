package cn.enncy.mybatis.core.result;


import cn.enncy.mybatis.core.SqlStringHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * //TODO
 * <br/>Created in 23:24 2021/11/18
 *
 * @author enncy
 */
public class MapResultHandler implements ResultSetHandler{


    @Override
    public Object handle(ResultSet resultSet, Map<String, Class<?>> resultMap, Class<?>  resultType) throws SQLException {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        for (Map.Entry<String, Class<?>> entry : resultMap.entrySet()) {
            if(resultSet.next()){
                map.put(entry.getKey(), resultSet.getObject(entry.getKey(), entry.getValue()));
            }
        }
        return map;
    }
}
