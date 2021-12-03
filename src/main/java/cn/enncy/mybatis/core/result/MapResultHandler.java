package cn.enncy.mybatis.core.result;


import cn.enncy.mybatis.core.SqlStringHandler;

import java.lang.reflect.InvocationTargetException;
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
public class MapResultHandler implements ResultSetHandler {
    ResultSet resultSet;
    Map<String, Class<?>> resultMap;

    public MapResultHandler(ResultSet resultSet, Map<String, Class<?>> resultMap) {
        this.resultSet = resultSet;
        this.resultMap = resultMap;
    }

    public Map<String, Object> handleMap() throws SQLException {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        for (Map.Entry<String, Class<?>> entry : resultMap.entrySet()) {
            map.put(entry.getKey(), resultSet.getObject(entry.getKey(), entry.getValue()));
        }
        return map;
    }

    @Override
    public Object handle() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return handleMap();
    }
}
